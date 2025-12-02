package com.stoq.StockWise.combo.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para la solicitud de creación de un combo.
 */
data class CreateComboRequestDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("items")
    val items: List<CreateComboItemRequestDto>
)

/**
 * DTO para un item en la solicitud de creación de combo.
 */
data class CreateComboItemRequestDto(
    @SerializedName("productId")
    val productId: Int,

    @SerializedName("quantity")
    val quantity: Int
)
