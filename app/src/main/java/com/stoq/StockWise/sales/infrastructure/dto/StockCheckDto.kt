package com.stoq.StockWise.sales.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para verificación de stock.
 */
data class StockCheckDto(
    @SerializedName("product_id")
    val productId: Int?,

    @SerializedName("product_name")
    val productName: String?,

    @SerializedName("available_stock")
    val availableStock: Int?,

    @SerializedName("unit_price")
    val unitPrice: Double?
)
