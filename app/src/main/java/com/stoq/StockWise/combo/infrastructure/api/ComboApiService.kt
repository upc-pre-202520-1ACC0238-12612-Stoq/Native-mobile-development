package com.stoq.StockWise.combo.infrastructure.api

import com.stoq.StockWise.combo.infrastructure.dto.ComboDto
import com.stoq.StockWise.combo.infrastructure.dto.CreateComboRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Servicio API para operaciones de combos
 */
interface ComboApiService {

    @GET("api/v1/combos")
    suspend fun getCombos(): Response<List<ComboDto>>

    @POST("api/v1/combos")
    suspend fun createCombo(@Body request: CreateComboRequestDto): Response<ComboDto>
}
