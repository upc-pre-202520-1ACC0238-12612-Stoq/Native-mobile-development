package com.stoq.StockWise.combo.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para un combo completo.
 */
data class ComboDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("items")
    val items: List<ComboItemDto>?
)
