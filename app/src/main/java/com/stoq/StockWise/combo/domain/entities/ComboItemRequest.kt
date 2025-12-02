package com.stoq.StockWise.combo.domain.entities

/**
 * Entidad de dominio para la solicitud de creación de un item de combo.
 * Representa un producto con su cantidad en un combo.
 */
data class ComboItemRequest(
    val productId: Int,
    val quantity: Int
)
