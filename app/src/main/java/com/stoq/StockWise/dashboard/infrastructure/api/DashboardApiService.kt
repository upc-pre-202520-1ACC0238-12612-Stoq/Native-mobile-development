package com.stoq.StockWise.dashboard.infrastructure.api

import com.stoq.StockWise.dashboard.infrastructure.dto.InventoryResponseDto
import com.stoq.StockWise.dashboard.infrastructure.dto.ProductDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Servicio API para operaciones del dashboard
 */
interface DashboardApiService {

    @GET("api/v1/products")
    suspend fun getProducts(): Response<List<ProductDto>>

    @GET("api/v1/inventory")
    suspend fun getInventory(): Response<InventoryResponseDto>
}