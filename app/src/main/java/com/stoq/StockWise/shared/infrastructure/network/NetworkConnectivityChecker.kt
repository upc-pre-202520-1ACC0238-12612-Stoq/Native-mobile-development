package com.stoq.StockWise.shared.infrastructure.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Utilidad para verificar la conectividad de red y disponibilidad del servidor.
 * Pertenece al Shared Kernel ya que es utilizada por múltiples bounded contexts.
 */
object NetworkConnectivityChecker {
    
    /**
     * Verifica si hay conectividad de red disponible
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected == true
        }
    }
    
    /**
     * Verifica si el servidor está disponible en la red
     */
    fun isServerReachable(host: String, port: Int, timeoutMs: Int = 5000): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress(host, port), timeoutMs)
            socket.close()
            true
        } catch (e: Exception) {
            println("Server unreachable: ${e.message}")
            false
        }
    }
    
    /**
     * Verifica la conectividad completa: red disponible y servidor alcanzable
     */
    fun isFullyConnected(context: Context, host: String, port: Int): Boolean {
        return isNetworkAvailable(context) && isServerReachable(host, port)
    }
    
    /**
     * Obtiene un mensaje de error descriptivo basado en el estado de conectividad
     */
    fun getConnectivityErrorMessage(context: Context, host: String, port: Int): String {
        return when {
            !isNetworkAvailable(context) -> "No hay conexión a internet. Verifica tu conexión de red."
            !isServerReachable(host, port) -> "No se puede conectar al servidor. El servidor puede estar temporalmente no disponible."
            else -> "Error de conectividad desconocido."
        }
    }
}
