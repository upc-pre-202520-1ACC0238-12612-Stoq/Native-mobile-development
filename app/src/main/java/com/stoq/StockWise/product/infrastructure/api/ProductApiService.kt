package com.stoq.StockWise.product.infrastructure.api

import com.stoq.StockWise.product.infrastructure.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Servicio API para operaciones de productos
 */
interface ProductApiService {
    
    @GET("api/v1/products")
    suspend fun getAllProducts(): Response<List<ProductDto>>
    
    @GET("api/v1/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductDto>
}