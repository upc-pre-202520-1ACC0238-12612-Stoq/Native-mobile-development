package com.stoq.StockWise.sales.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de venta.
 */
data class SaleResponseDto(
    @SerializedName("id")
    val id: String?,

    @SerializedName("product_id")
    val productId: Int?,

    @SerializedName("product_name")
    val productName: String?,

    @SerializedName("quantity")
    val quantity: Int?,

    @SerializedName("unit_price")
    val unitPrice: Double?,

    @SerializedName("total")
    val total: Double?,

    @SerializedName("customer_name")
    val customerName: String?,

    @SerializedName("notes")
    val notes: String?,

    @SerializedName("created_at")
    val createdAt: String?,

    @SerializedName("stock_remaining")
    val stockRemaining: Int?
)
