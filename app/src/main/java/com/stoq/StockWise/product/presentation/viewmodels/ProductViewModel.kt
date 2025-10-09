package com.stoq.StockWise.product.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import kotlinx.coroutines.launch

/**
 * Estado de la pantalla de productos
 */
data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val filteredProducts: List<Product> = emptyList()
)

/**
 * ViewModel para manejar el estado de la pantalla de productos
 */
class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    
    var uiState by mutableStateOf(ProductUiState())
        private set
    
    init {
        loadProducts()
    }
    
    /**
     * Carga todos los productos desde la API
     */
    fun loadProducts() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            
            productRepository.getAllProducts()
                .onSuccess { products ->
                    uiState = uiState.copy(
                        products = products,
                        filteredProducts = products,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error desconocido"
                    )
                }
        }
    }
    
    /**
     * Actualiza la consulta de búsqueda y filtra los productos
     */
    fun updateSearchQuery(query: String) {
        uiState = uiState.copy(searchQuery = query)
        filterProducts(query)
    }
    
    /**
     * Filtra los productos basándose en la consulta de búsqueda
     */
    private fun filterProducts(query: String) {
        val filtered = if (query.isBlank()) {
            uiState.products
        } else {
            uiState.products.filter { product ->
                val name = product.name ?: ""
                val description = product.description ?: ""
                name.contains(query, ignoreCase = true) ||
                description.contains(query, ignoreCase = true)
            }
        }
        uiState = uiState.copy(filteredProducts = filtered)
    }
    
    /**
     * Limpia el mensaje de error
     */
    fun clearError() {
        uiState = uiState.copy(errorMessage = null)
    }
    
    /**
     * Recarga los productos
     */
    fun refreshProducts() {
        loadProducts()
    }
}