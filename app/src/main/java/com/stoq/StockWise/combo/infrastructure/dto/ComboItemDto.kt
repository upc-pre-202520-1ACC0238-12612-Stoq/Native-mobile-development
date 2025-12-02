package com.stoq.StockWise.combo.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para un item dentro de un combo.
 */
data class ComboItemDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("productId")
    val productId: Int?,

    @SerializedName("productName")
    val productName: String?,

    @SerializedName("productDescription")
    val productDescription: String?,

    @SerializedName("productPrice")
    val productPrice: Double?,

    @SerializedName("quantity")
    val quantity: Int?
)
