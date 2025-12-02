package com.stoq.StockWise.sales.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import com.stoq.StockWise.sales.domain.entities.SaleRequest
import com.stoq.StockWise.sales.domain.entities.SaleItem
import com.stoq.StockWise.sales.domain.usecases.CheckStockUseCase
import com.stoq.StockWise.sales.domain.usecases.CreateSaleUseCase
import kotlinx.coroutines.launch

class SalesViewModel(
    private val createSaleUseCase: CreateSaleUseCase,
    private val checkStockUseCase: CheckStockUseCase,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(SalesUiState())
    val uiState: SalesUiState get() = _uiState.value

    init {
        loadProducts()
    }

    // ----------------------------------------------------------
    // CARGA DE PRODUCTOS
    // ----------------------------------------------------------
    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingProducts = true)

            try {
                val result = productRepository.getAllProducts()

                if (result.isSuccess) {
                    val products = result.getOrThrow()
                        .filter { it.id != null }
                        .sortedBy { it.name ?: "" }

                    _uiState.value = _uiState.value.copy(
                        products = products,
                        filteredProducts = products,
                        isLoadingProducts = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoadingProducts = false,
                        error = "No se pudieron cargar los productos"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingProducts = false,
                    error = e.message ?: "Error desconocido al cargar productos"
                )
            }
        }
    }

    // ----------------------------------------------------------
// FUNCIONES PARA CARRITO DE COMPRAS
// ----------------------------------------------------------

    /**
     * Inicia la edición de un producto (muestra el panel de cantidad)
     */
    fun startSelectingProduct(product: Product) {
        _uiState.value = _uiState.value.copy(
            currentEditingProduct = product,
            currentQuantity = 1, // Valor por defecto
            error = null
        )
    }

    /**
     * Actualiza la cantidad del producto que se está editando
     */
    fun updateProductQuantity(productId: Int, quantity: Int) {
        if (quantity < 0) return

        _uiState.value = _uiState.value.copy(
            currentQuantity = quantity
        )
    }

    /**
     * Agrega el producto actual al carrito
     */
    fun addToCart(product: Product) {
        val state = _uiState.value
        val quantity = state.currentQuantity

        if (quantity <= 0) {
            _uiState.value = state.copy(error = "La cantidad debe ser mayor a 0")
            return
        }

        // Verificar si el producto ya está en el carrito
        val existingItem = state.cart.find { it.product.id == product.id }

        val newCart = if (existingItem != null) {
            // Si ya existe, no duplicar, mantener el existente
            state.cart
        } else {
            // Si no existe, agregar nuevo item
            val total = (product.salePrice ?: 0.0) * quantity
            val newItem = CartItem(product, quantity, total)
            state.cart + newItem
        }

        val newCartTotal = newCart.sumOf { it.total }

        _uiState.value = state.copy(
            cart = newCart,
            cartTotal = newCartTotal,
            currentEditingProduct = null, // Limpiar edición
            currentQuantity = 0,
            error = null
        )
    }

    /**
     * Remueve un producto del carrito
     */
    fun removeFromCart(productId: Int) {
        val state = _uiState.value
        val newCart = state.cart.filter { it.product.id != productId }
        val newCartTotal = newCart.sumOf { it.total }

        _uiState.value = state.copy(
            cart = newCart,
            cartTotal = newCartTotal
        )
    }

    /**
     * Limpia todo el carrito
     */
    fun clearCart() {
        _uiState.value = _uiState.value.copy(
            cart = emptyList(),
            cartTotal = 0.0,
            currentEditingProduct = null,
            currentQuantity = 0
        )
    }

    /**
     * Calcula el total del carrito
     */
    fun calculateCartTotal(): Double {
        return _uiState.value.cart.sumOf { it.total }
    }

    // ----------------------------------------------------------
    // BUSCADOR DE PRODUCTOS (🔥 NUEVO Y CORREGIDO)
    // ----------------------------------------------------------
    fun updateSearchQuery(query: String) {
        val allProducts = _uiState.value.products

        val filtered = if (query.isBlank()) {
            allProducts
        } else {
            allProducts.filter {
                val q = query.lowercase()
                (it.name?.lowercase()?.contains(q) == true) ||
                        (it.description?.lowercase()?.contains(q) == true)
            }
        }

        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredProducts = filtered
        )
    }


    // ----------------------------------------------------------
    // FORMULARIO DE VENTA
    // ----------------------------------------------------------
    fun updateCustomerName(name: String) {
        _uiState.value = _uiState.value.copy(customerName = name.trim())
    }

    fun updateNotes(notes: String) {
        _uiState.value = _uiState.value.copy(notes = notes.trim())
    }

    /**
     * Verifica si el formulario es válido para realizar la venta
     */
    fun isFormValid(): Boolean {
        val state = _uiState.value
        return state.cart.isNotEmpty() &&
                state.customerName.isNotBlank()
    }

    /**
     * Obtiene el total del carrito formateado como string
     */
    fun getTotalFormatted(): String {
        return String.format("$%.2f", _uiState.value.cartTotal)
    }

    // ----------------------------------------------------------
// REGISTRAR VENTA
// ----------------------------------------------------------
    fun createSale() {
        val state = _uiState.value

        val cart = state.cart
        val customerName = state.customerName

        if (cart.isEmpty()) {
            _uiState.value = state.copy(error = "El carrito está vacío")
            return
        }

        if (customerName.isBlank()) {
            _uiState.value = state.copy(error = "El nombre del cliente es obligatorio")
            return
        }

        // Crear items de venta desde el carrito
        val saleItems = cart.map { cartItem ->
            SaleItem.create(cartItem.product, cartItem.quantity)
        }

        val request = SaleRequest(
            items = saleItems,
            customerName = customerName,
            notes = state.notes.takeIf { it.isNotBlank() }
        )

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)

            val result = createSaleUseCase(request)

            if (result.isSuccess) {
                val response = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    saleSuccess = true,
                    successMessage = "Venta realizada! ID: ${response.id}",
                    error = null,
                    cart = emptyList(), // Limpiar carrito después de venta exitosa
                    cartTotal = 0.0,
                    customerName = "",
                    notes = ""
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Error al realizar la venta"
                )
            }
        }
    }

    // ----------------------------------------------------------
    // MODAL DE BOLETA
    // ----------------------------------------------------------
    fun openReceipt() {
        _uiState.value = _uiState.value.copy(showReceipt = true)
    }

    fun closeReceipt() {
        _uiState.value = _uiState.value.copy(showReceipt = false)
    }

    // ----------------------------------------------------------
// FUNCIONES DE UTILIDAD
// ----------------------------------------------------------

}
