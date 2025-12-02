package com.stoq.StockWise.sales.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para un item individual en una venta.
 */
data class SaleItemDto(
    @SerializedName("product_id")
    val productId: Int?,

    @SerializedName("quantity")
    val quantity: Int?,

    @SerializedName("subtotal")
    val subtotal: Double?,

    @SerializedName("product_name")
    val productName: String?,

    @SerializedName("unit_price")
    val unitPrice: Double?
)
