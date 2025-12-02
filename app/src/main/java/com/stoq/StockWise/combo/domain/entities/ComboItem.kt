package com.stoq.StockWise.combo.domain.entities

/**
 * Entidad de dominio que representa un item dentro de un combo.
 */
data class ComboItem(
    val id: Int,
    val productId: Int,
    val productName: String,
    val productDescription: String,
    val productPrice: Double,
    val quantity: Int
)
