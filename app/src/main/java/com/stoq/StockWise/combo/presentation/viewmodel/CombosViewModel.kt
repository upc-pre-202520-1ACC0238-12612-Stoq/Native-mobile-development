package com.stoq.StockWise.combo.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.combo.domain.entities.Combo
import com.stoq.StockWise.combo.domain.entities.ComboItemRequest
import com.stoq.StockWise.combo.domain.usecase.CreateComboUseCase
import com.stoq.StockWise.combo.domain.usecase.GetCombosUseCase
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import kotlinx.coroutines.launch

class CombosViewModel(
    private val getCombos: GetCombosUseCase,
    private val createCombo: CreateComboUseCase,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(CombosUiState())
    val uiState: CombosUiState get() = _uiState.value

    init {
        loadCombos()
    }

    fun loadCombos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val result = getCombos()
                if (result.isSuccess) {
                    val combos = result.getOrThrow()
                    _uiState.value = _uiState.value.copy(
                        combos = combos,
                        filteredCombos = combos,
                        isLoading = false,
                        error = null
                    )
                } else {
                    val error = result.exceptionOrNull()
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error?.message ?: "Error al obtener combos"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun loadProductsForComboCreation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingProducts = true)

            try {
                val result = productRepository.getAllProducts()
                if (result.isSuccess) {
                    val products = result.getOrThrow()
                        .filter { it.id != null } // Importante

                    _uiState.value = _uiState.value.copy(
                        availableProducts = products,
                        isLoadingProducts = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoadingProducts = false,
                        error = "No se pudieron cargar productos"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingProducts = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun updateComboName(name: String) {
        _uiState.value = _uiState.value.copy(comboName = name)
    }

    fun addProductToCombo(productId: Int) {
        val updated = _uiState.value.selectedProducts.toMutableMap()
        updated[productId] = 1
        _uiState.value = _uiState.value.copy(selectedProducts = updated)
    }

    fun removeProductFromCombo(productId: Int) {
        val updated = _uiState.value.selectedProducts.toMutableMap()
        updated.remove(productId)
        _uiState.value = _uiState.value.copy(selectedProducts = updated)
    }

    fun updateProductQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeProductFromCombo(productId)
            return
        }
        val updated = _uiState.value.selectedProducts.toMutableMap()
        updated[productId] = quantity
        _uiState.value = _uiState.value.copy(selectedProducts = updated)
    }

    fun createNewCombo() {
        val name = _uiState.value.comboName.trim()
        if (name.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "El nombre del combo es obligatorio")
            return
        }

        val selected = _uiState.value.selectedProducts
        if (selected.isEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Seleccione al menos un producto")
            return
        }

        val items = selected.map { ComboItemRequest(it.key, it.value) }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreatingCombo = true)

            try {
                val result = createCombo(name, items)

                if (result.isSuccess) {
                    val newCombo = result.getOrThrow()
                    val updatedList = _uiState.value.combos + newCombo

                    _uiState.value = _uiState.value.copy(
                        combos = updatedList,
                        filteredCombos = updatedList,
                        isCreatingCombo = false,
                        error = null
                    )

                    resetComboCreation()
                } else {
                    val error = result.exceptionOrNull()
                    _uiState.value = _uiState.value.copy(
                        isCreatingCombo = false,
                        error = error?.message ?: "Error al crear combo"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isCreatingCombo = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun resetComboCreation() {
        _uiState.value = _uiState.value.copy(
            comboName = "",
            selectedProducts = emptyMap(),
            error = null,
            isCreatingCombo = false
        )
    }

    fun getSelectedProductsWithDetails(): List<Triple<Product, Int, Double>> {
        return _uiState.value.availableProducts
            .filter { _uiState.value.selectedProducts.containsKey(it.id) }
            .map { product ->
                val quantity = _uiState.value.selectedProducts[product.id] ?: 0
                val subtotal = (product.salePrice ?: 0.0) * quantity
                Triple(product, quantity, subtotal)
            }
    }

    fun getTotalPrice(): Double {
        return getSelectedProductsWithDetails().sumOf { it.third }
    }

    fun updateSearchQuery(query: String) {
        val filtered = _uiState.value.combos.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredCombos = filtered
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
