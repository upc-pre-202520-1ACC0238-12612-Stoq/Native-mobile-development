package com.stoq.StockWise.combo.domain.entities

/**
 * Entidad de dominio que representa un combo en el sistema de inventario.
 */
data class Combo(
    val id: Int,
    val name: String,
    val items: List<ComboItem>
)
