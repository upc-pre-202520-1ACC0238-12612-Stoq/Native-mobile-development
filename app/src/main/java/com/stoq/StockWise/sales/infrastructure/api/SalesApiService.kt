package com.stoq.StockWise.sales.infrastructure.api

import com.stoq.StockWise.sales.infrastructure.dto.SaleRequestDto
import com.stoq.StockWise.sales.infrastructure.dto.SaleResponseDto
import com.stoq.StockWise.sales.infrastructure.dto.StockCheckDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Servicio API para operaciones de ventas
 */
interface SalesApiService {

    @POST("api/v1/sales")
    suspend fun createSale(@Body request: SaleRequestDto): Response<SaleResponseDto>

    @GET("api/v1/sales/{id}")
    suspend fun getSale(@Path("id") id: String): Response<SaleResponseDto>

    @GET("api/v1/sales/check-stock/{productId}")
    suspend fun checkStock(@Path("productId") productId: Int): Response<StockCheckDto>
}
