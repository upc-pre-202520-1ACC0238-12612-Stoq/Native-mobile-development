package com.stoq.StockWise.sales.domain.entities

/**
 * Entidad de dominio que representa la verificación de stock de un producto.
 */
data class StockCheck(
    val productId: Int,
    val productName: String,
    val availableStock: Int,
    val unitPrice: Double
) {
    /**
     * Verifica si hay suficiente stock para la cantidad solicitada
     */
    fun hasEnoughStock(requestedQuantity: Int): Boolean {
        return availableStock >= requestedQuantity
    }

    /**
     * Calcula el total que costaría la cantidad solicitada
     */
    fun calculateTotal(requestedQuantity: Int): Double {
        return unitPrice * requestedQuantity
    }

    /**
     * Obtiene el precio formateado
     */
    fun getFormattedPrice(): String {
        return String.format("$%.2f", unitPrice)
    }

    /**
     * Obtiene el estado del stock como string
     */
    fun getStockStatus(): String {
        return when {
            availableStock == 0 -> "Sin stock disponible"
            availableStock <= 5 -> "Stock bajo: $availableStock unidades"
            else -> "Stock disponible: $availableStock unidades"
        }
    }

    /**
     * Verifica si el producto está disponible para venta
     */
    fun isAvailable(): Boolean {
        return availableStock > 0
    }
}
