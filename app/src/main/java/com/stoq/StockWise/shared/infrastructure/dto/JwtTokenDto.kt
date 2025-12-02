package com.stoq.StockWise.shared.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para el manejo de JWT tokens en la capa de infraestructura.
 * Este DTO pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 */
data class JwtTokenDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("expires_at")
    val expiresAt: Long? = null,
    @SerializedName("issued_at")
    val issuedAt: Long? = null
)
