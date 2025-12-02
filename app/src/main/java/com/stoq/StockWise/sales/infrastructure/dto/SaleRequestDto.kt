package com.stoq.StockWise.sales.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para solicitud de venta con múltiples items.
 */
data class SaleRequestDto(
    @SerializedName("items")
    val items: List<SaleItemDto>?,

    @SerializedName("customer_name")
    val customerName: String?,

    @SerializedName("notes")
    val notes: String?
)
