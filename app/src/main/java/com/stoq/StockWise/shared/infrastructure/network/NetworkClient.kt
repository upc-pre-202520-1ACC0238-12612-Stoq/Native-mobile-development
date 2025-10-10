package com.stoq.StockWise.shared.infrastructure.network

import com.stoq.StockWise.product.infrastructure.api.ProductApiService
import com.stoq.StockWise.inventory.infrastructure.api.InventoryApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Cliente de red para configurar Retrofit y crear instancias de servicios API
 */
object NetworkClient {
    
    /**
     * Interceptor para agregar automáticamente el token de autorización
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestWithAuth = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${ApiConfig.AUTH_TOKEN}")
            .build()
        chain.proceed(requestWithAuth)
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(ApiConfig.CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(ApiConfig.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(ApiConfig.WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    /**
     * Crea una instancia del servicio API de productos
     */
    fun createProductApiService(): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }
    
    /**
     * Crea una instancia del servicio API de inventarios
     */
    fun createInventoryApiService(): InventoryApiService {
        return retrofit.create(InventoryApiService::class.java)
    }
}