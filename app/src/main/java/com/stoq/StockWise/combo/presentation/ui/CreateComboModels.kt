package com.stoq.StockWise.combo.presentation.ui

/**
 * Modelo simplificado para items del combo en la pantalla de creación.
 * Usado para pasar datos al callback onCreateCombo.
 */
data class ComboItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val unitPrice: Double
)
