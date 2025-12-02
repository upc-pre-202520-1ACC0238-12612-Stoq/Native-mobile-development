package com.stoq.StockWise.sales.domain.entities

/**
 * Entidad de dominio que representa la respuesta de una venta realizada.
 */
data class SaleResponse(
    val id: String,
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val unitPrice: Double,
    val total: Double,
    val customerName: String,
    val notes: String? = null,
    val createdAt: String,
    val stockRemaining: Int
) {
    /**
     * Calcula el total formateado como string con símbolo de moneda
     */
    fun getFormattedTotal(): String {
        return String.format("$%.2f", total)
    }

    /**
     * Calcula el precio unitario formateado
     */
    fun getFormattedUnitPrice(): String {
        return String.format("$%.2f", unitPrice)
    }

    /**
     * Obtiene el estado del stock después de la venta
     */
    fun getStockStatus(): String {
        return when {
            stockRemaining == 0 -> "Sin stock"
            stockRemaining <= 5 -> "Stock bajo ($stockRemaining)"
            else -> "Stock disponible ($stockRemaining)"
        }
    }

    /**
     * Verifica si la venta fue exitosa
     */
    fun isSuccessful(): Boolean {
        return id.isNotBlank() && total > 0.0
    }
}
