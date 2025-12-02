package com.stoq.StockWise.combo.presentation.ui

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.stoq.StockWise.product.domain.entities.Product

/**
 * Estado UI para la pantalla de creación de combos
 */
data class CreateComboUiState(
    val comboName: String = "",
    val selectedProducts: Map<Int, Int> = emptyMap(),
    val availableProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * ViewModel simplificado para la pantalla de creación de combos.
 * Gestiona el estado de selección de productos y creación del combo.
 */
class CreateComboViewModel : ViewModel() {

    private val _uiState = mutableStateOf(CreateComboUiState())
    val uiState: CreateComboUiState get() = _uiState.value

    // Estado mutable para productos seleccionados como especificado por el usuario
    val selectedProducts = mutableStateMapOf<Int, Int>()

    /**
     * Actualiza el nombre del combo
     */
    fun updateComboName(name: String) {
        _uiState.value = _uiState.value.copy(comboName = name, errorMessage = null)
    }

    /**
     * Establece la lista de productos disponibles
     */
    fun setAvailableProducts(products: List<Product>) {
        _uiState.value = _uiState.value.copy(availableProducts = products)
    }

    /**
     * Toggle la selección de un producto.
     * Si está seleccionado, lo remueve. Si no, lo agrega con cantidad 1.
     */
    fun toggleProductSelection(product: Product) {
        val productId = product.id ?: return

        if (selectedProducts.containsKey(productId)) {
            // Remover producto
            selectedProducts.remove(productId)
        } else {
            // Agregar producto con cantidad 1
            selectedProducts[productId] = 1
        }

        // Actualizar estado
        _uiState.value = _uiState.value.copy(
            selectedProducts = selectedProducts.toMap(),
            errorMessage = null
        )
    }

    /**
     * Incrementa la cantidad de un producto
     */
    fun increaseQty(product: Product) {
        val productId = product.id ?: return
        val currentQty = selectedProducts[productId] ?: 0
        val newQty = (currentQty + 1).coerceAtMost(99) // Máximo 99

        selectedProducts[productId] = newQty
        _uiState.value = _uiState.value.copy(
            selectedProducts = selectedProducts.toMap()
        )
    }

    /**
     * Decrementa la cantidad de un producto.
     * Si llega a 0, remueve el producto de la selección.
     */
    fun decreaseQty(product: Product) {
        val productId = product.id ?: return
        val currentQty = selectedProducts[productId] ?: 0

        if (currentQty <= 1) {
            // Si es 1 o menos, remover el producto
            selectedProducts.remove(productId)
        } else {
            // Decrementar cantidad
            selectedProducts[productId] = currentQty - 1
        }

        _uiState.value = _uiState.value.copy(
            selectedProducts = selectedProducts.toMap()
        )
    }

    /**
     * Obtiene la cantidad actual de un producto
     */
    fun getProductQuantity(product: Product): Int {
        return selectedProducts[product.id ?: return 0] ?: 0
    }

    /**
     * Verifica si un producto está seleccionado
     */
    fun isProductSelected(product: Product): Boolean {
        return selectedProducts.containsKey(product.id)
    }

    /**
     * Calcula el total del combo usando sumOf como solicitado
     */
    fun getTotalPrice(): Double {
        return selectedProducts.entries.sumOf { (productId, quantity) ->
            val product = uiState.availableProducts.find { it.id == productId }
            val unitPrice = product?.salePrice ?: 0.0
            unitPrice * quantity
        }
    }

    /**
     * Obtiene la lista de productos seleccionados con detalles para el resumen
     */
    fun getSelectedProductsWithDetails(): List<Triple<Product, Int, Double>> {
        return selectedProducts.entries.mapNotNull { (productId, quantity) ->
            val product = uiState.availableProducts.find { it.id == productId }
            product?.let {
                val subtotal = (it.salePrice ?: 0.0) * quantity
                Triple(it, quantity, subtotal)
            }
        }
    }

    /**
     * Valida y crea el combo.
     * Retorna true si es válido, false si hay errores.
     */
    fun validateAndCreateCombo(): Boolean {
        val name = uiState.comboName.trim()

        // Validación: nombre requerido
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "El nombre del combo es obligatorio"
            )
            return false
        }

        // Validación: al menos un producto seleccionado
        if (selectedProducts.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Debe seleccionar al menos un producto"
            )
            return false
        }

        // Si todo está bien, limpiar errores
        _uiState.value = _uiState.value.copy(errorMessage = null)
        return true
    }

    /**
     * Obtiene los items del combo para pasar al callback onCreateCombo
     */
    fun getComboItems(): List<ComboItem> {
        return selectedProducts.entries.mapNotNull { (productId, quantity) ->
            val product = uiState.availableProducts.find { it.id == productId }
            product?.let {
                ComboItem(
                    id = productId,
                    name = it.name ?: "Sin nombre",
                    quantity = quantity,
                    unitPrice = it.salePrice ?: 0.0
                )
            }
        }
    }

    /**
     * Resetea el estado de creación del combo
     */
    fun reset() {
        selectedProducts.clear()
        _uiState.value = CreateComboUiState(
            availableProducts = uiState.availableProducts // Mantener productos disponibles
        )
    }
}
