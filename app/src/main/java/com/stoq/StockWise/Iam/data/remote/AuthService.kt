package com.stoq.StockWise.Iam.data.remote

import android.content.Context
import com.stoq.StockWise.Iam.data.model.LoginRequest
import com.stoq.StockWise.Iam.data.model.LoginResponse
import com.stoq.StockWise.Iam.data.model.RegisterRequest
import com.stoq.StockWise.shared.infrastructure.network.ApiConfig
import com.stoq.StockWise.shared.infrastructure.network.NetworkConnectivityChecker
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.HttpStatusCode
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

interface AuthService {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun register(request: RegisterRequest): Result<LoginResponse>
}

class AuthServiceImpl(
    private val client: HttpClient,
    private val context: Context
) : AuthService {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        // Verificar solo conectividad básica de red
        if (!NetworkConnectivityChecker.isNetworkAvailable(context)) {
            return Result.failure(Exception("No hay conexión a internet. Verifica tu conexión de red."))
        }
        return try {
            val fullUrl = "${ApiConfig.BASE_URL}/${ApiConfig.IdentityAccess.LOGIN}"
            val response = client.post(fullUrl) {
                contentType(ContentType.Application.Json)
                setBody(request)
                header("Accept", "application/json")
                header("User-Agent", "StockWise-Android/1.0")
            }
            
            val responseBody = response.body<LoginResponse>()
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    println("Login successful for user: ${request.email}")
                    Result.success(responseBody)
                }
                HttpStatusCode.Unauthorized -> {
                    println("Authentication failed for user: ${request.email}")
                    Result.failure(Exception("Invalid credentials"))
                }
                HttpStatusCode.BadRequest -> {
                    println("Bad request: Invalid request format")
                    Result.failure(Exception("Invalid request"))
                }
                else -> {
                    println("Server error: ${response.status.value} - ${response.status.description}")
                    Result.failure(Exception("Server error: ${response.status.value}"))
                }
            }
        } catch (e: ConnectTimeoutException) {
            println("Connection timeout: ${e.message}")
            Result.failure(Exception("Tiempo de conexión agotado. Verifica tu conexión a internet."))
        } catch (e: IOException) {
            println("Network error: ${e.message}")
            when {
                e.message?.contains("Unable to resolve host") == true -> {
                    Result.failure(Exception("No se puede conectar al servidor. Verifica tu conexión a internet."))
                }
                e.message?.contains("Connection refused") == true -> {
                    Result.failure(Exception("El servidor no está disponible. Intenta más tarde."))
                }
                e.message?.contains("timeout") == true -> {
                    Result.failure(Exception("Tiempo de espera agotado. Verifica tu conexión a internet."))
                }
                else -> {
                    Result.failure(Exception("Error de red: ${e.message}. Verifica tu conexión a internet."))
                }
            }
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            Result.failure(Exception("Error inesperado: ${e.message}"))
        }
    }

    override suspend fun register(request: RegisterRequest): Result<LoginResponse> {
        // Verificar solo conectividad básica de red
        if (!NetworkConnectivityChecker.isNetworkAvailable(context)) {
            return Result.failure(Exception("No hay conexión a internet. Verifica tu conexión de red."))
        }
        
        return try {
            val fullUrl = "${ApiConfig.BASE_URL}/${ApiConfig.IdentityAccess.REGISTER}"
            val response = client.post(fullUrl) {
                contentType(ContentType.Application.Json)
                setBody(request)
                header("Accept", "application/json")
                header("User-Agent", "StockWise-Android/1.0")
            }
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    println("Registration successful for user: ${request.email}")
                    Result.success(response.body())
                }
                HttpStatusCode.BadRequest -> {
                    println("Bad request: Invalid registration data")
                    Result.failure(Exception("Datos de registro inválidos"))
                }
                HttpStatusCode.Conflict -> {
                    println("User already exists: ${request.email}")
                    Result.failure(Exception("El usuario ya existe"))
                }
                else -> {
                    println("Server error: ${response.status.value} - ${response.status.description}")
                    Result.failure(Exception("Error del servidor: ${response.status.value}"))
                }
            }
        } catch (e: ConnectTimeoutException) {
            println("Connection timeout: ${e.message}")
            Result.failure(Exception("Tiempo de conexión agotado. Verifica tu conexión a internet."))
        } catch (e: IOException) {
            println("Network error: ${e.message}")
            when {
                e.message?.contains("Unable to resolve host") == true -> {
                    Result.failure(Exception("No se puede conectar al servidor. Verifica tu conexión a internet."))
                }
                e.message?.contains("Connection refused") == true -> {
                    Result.failure(Exception("El servidor no está disponible. Intenta más tarde."))
                }
                e.message?.contains("timeout") == true -> {
                    Result.failure(Exception("Tiempo de espera agotado. Verifica tu conexión a internet."))
                }
                else -> {
                    Result.failure(Exception("Error de red: ${e.message}. Verifica tu conexión a internet."))
                }
            }
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            Result.failure(Exception("Error inesperado: ${e.message}"))
        }
    }
}