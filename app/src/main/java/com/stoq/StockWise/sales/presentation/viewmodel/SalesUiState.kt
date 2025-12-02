package com.stoq.StockWise.sales.presentation.viewmodel

import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.sales.domain.entities.SaleItem
import com.stoq.StockWise.sales.domain.entities.StockCheck

/**
 * Representa un item en el carrito de compras
 */
data class CartItem(
    val product: Product,
    val quantity: Int,
    val total: Double
)

data class SalesUiState(

    val isLoading: Boolean = false,
    val isLoadingProducts: Boolean = false,

    // ESTOS DOS CAMPOS SON OBLIGATORIOS PARA QUE updateSearchQuery FUNCIONE
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),

    // Sistema de carrito de compras
    val currentEditingProduct: Product? = null,
    val currentQuantity: Int = 0,
    val cart: List<CartItem> = emptyList(),
    val cartTotal: Double = 0.0,

    // Formulario para la venta completa
    val customerName: String = "",
    val notes: String = "",

    // Errores y éxito
    val error: String? = null,
    val saleSuccess: Boolean = false,
    val successMessage: String? = null,

    // Búsqueda
    val searchQuery: String = "",

    // Modal de boleta
    val showReceipt: Boolean = false
)
