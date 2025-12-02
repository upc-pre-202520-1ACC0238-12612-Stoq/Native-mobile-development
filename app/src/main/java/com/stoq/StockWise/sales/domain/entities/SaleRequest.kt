package com.stoq.StockWise.sales.domain.entities

/**
 * Entidad de dominio que representa una solicitud de venta.
 * Ahora maneja múltiples items en lugar de un solo producto.
 */
data class SaleRequest(
    val items: List<SaleItem>,
    val customerName: String,
    val notes: String? = null
) {
    /**
     * Valida que la solicitud de venta sea correcta
     */
    fun isValid(): Boolean {
        return items.isNotEmpty() &&
               items.all { it.isValid() } &&
               customerName.isNotBlank() &&
               getTotal() > 0.0
    }

    /**
     * Calcula el total de la venta sumando todos los subtotales
     */
    fun getTotal(): Double {
        return items.sumOf { it.subtotal }
    }

    /**
     * Obtiene el total de productos en la venta
     */
    fun getTotalQuantity(): Int {
        return items.sumOf { it.quantity }
    }

    /**
     * Obtiene una descripción formateada de la venta
     */
    fun getFormattedDescription(): String {
        val itemCount = items.size
        val totalQuantity = getTotalQuantity()
        val total = getTotal()
        return "Venta de $itemCount producto(s) ($totalQuantity unidad(es)) para ${customerName} - Total: $${"%.2f".format(total)}"
    }

    companion object {
        /**
         * Crea una SaleRequest desde un solo producto (compatibilidad hacia atrás)
         */
        fun fromSingleItem(productId: Int, quantity: Int, customerName: String, notes: String? = null): SaleRequest? {
            // Esta función se mantiene por compatibilidad pero debería ser eliminada eventualmente
            // ya que ahora trabajamos con múltiples items
            return null // No implementada ya que el flujo cambió completamente
        }
    }
}
