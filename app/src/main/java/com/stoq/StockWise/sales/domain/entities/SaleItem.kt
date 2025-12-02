package com.stoq.StockWise.sales.domain.entities

import com.stoq.StockWise.product.domain.entities.Product

/**
 * Entidad de dominio que representa un item individual en una venta.
 * Contiene el producto, cantidad y subtotal calculado.
 */
data class SaleItem(
    val product: Product,
    val quantity: Int,
    val subtotal: Double
) {
    /**
     * Valida que el item de venta sea correcto
     */
    fun isValid(): Boolean {
        return product.id != null && quantity > 0 && subtotal >= 0.0
    }

    /**
     * Obtiene una descripción formateada del item
     */
    fun getFormattedDescription(): String {
        return "${quantity}x ${product.name ?: "Producto sin nombre"} - $${"%.2f".format(subtotal)}"
    }

    /**
     * Calcula el subtotal basado en el precio de venta del producto
     */
    fun calculateSubtotal(): Double {
        val price = product.salePrice ?: 0.0
        return price * quantity
    }

    companion object {
        /**
         * Crea un SaleItem con subtotal calculado automáticamente
         */
        fun create(product: Product, quantity: Int): SaleItem {
            val subtotal = (product.salePrice ?: 0.0) * quantity
            return SaleItem(product, quantity, subtotal)
        }
    }
}
