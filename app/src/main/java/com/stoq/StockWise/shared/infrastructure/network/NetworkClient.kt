package com.stoq.StockWise.shared.infrastructure.network

import com.stoq.StockWise.product.infrastructure.api.ProductApiService
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente de red para configurar Retrofit y crear instancias de servicios API.
 * Este cliente pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 */
object NetworkClient {

    /**
     * Crea un interceptor para agregar automáticamente el token de autorización desde el repositorio JWT.
     *
     * @param jwtRepository El repositorio JWT para obtener el token de autenticación
     * @return Interceptor configurado con el token del repositorio
     */
    private fun createAuthInterceptor(jwtRepository: JwtRepository): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()

            // Obtener el token del repositorio
            val token = runBlocking {
                jwtRepository.getToken().getOrNull()?.value
            }

            // Agregar el header Authorization si el token existe
            val requestBuilder = originalRequest.newBuilder()
            if (token != null && token.isNotBlank()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            val requestWithAuth = requestBuilder.build()
            chain.proceed(requestWithAuth)
        }
    }

    /**
     * Crea un OkHttpClient configurado con el interceptor de autenticación.
     *
     * @param jwtRepository El repositorio JWT para obtener el token de autenticación
     * @return OkHttpClient configurado
     */
    private fun createOkHttpClient(jwtRepository: JwtRepository): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(ApiConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(ApiConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(ApiConfig.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(createAuthInterceptor(jwtRepository))
            .build()
    }

    /**
     * Crea una instancia de Retrofit configurada con autenticación JWT.
     *
     * @param jwtRepository El repositorio JWT para obtener el token de autenticación
     * @return Retrofit configurado
     */
    fun createRetrofit(jwtRepository: JwtRepository): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(createOkHttpClient(jwtRepository))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Crea una instancia del servicio API de productos con autenticación JWT.
     *
     * @param jwtRepository El repositorio JWT para obtener el token de autenticación
     * @return ProductApiService configurado
     */
    fun createProductApiService(jwtRepository: JwtRepository): ProductApiService {
        return createRetrofit(jwtRepository).create(ProductApiService::class.java)
    }

    /**
     * Crea una instancia del servicio API de inventarios con autenticación JWT.
     * Este método es genérico para evitar dependencias circulares entre módulos.
     *
     * @param jwtRepository El repositorio JWT para obtener el token de autenticación
     * @return InventoryApiService configurado
     */
    inline fun <reified T> createInventoryApiService(jwtRepository: JwtRepository): T {
        return createRetrofit(jwtRepository).create(T::class.java)
    }
}