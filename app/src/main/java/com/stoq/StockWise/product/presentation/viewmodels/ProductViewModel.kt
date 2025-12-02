package com.stoq.StockWise.product.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Estado de la pantalla de productos
 */
data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val filteredProducts: List<Product> = emptyList(),
    val showAddProductModal: Boolean = false,
    val isAddingProduct: Boolean = false,
    val addProductErrorMessage: String? = null,
    val showProductDetailModal: Boolean = false,
    val selectedProduct: Product? = null,
    val showEditProductModal: Boolean = false,
    val editingProduct: Product? = null,
    val isUpdatingProduct: Boolean = false,
    val updateProductErrorMessage: String? = null
)

/**
 * ViewModel para manejar el estado de  la pantalla de productos
 */
class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {
    
    var uiState by mutableStateOf(ProductUiState())
        private set
    
    init {
        loadProducts()
    }

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
    
    /**
     * Muestra el modal de agregar producto
     */
    fun showAddProductModal() {
        uiState = uiState.copy(showAddProductModal = true, addProductErrorMessage = null)
    }
    
    /**
     * Oculta el modal de agregar producto
     */
    fun hideAddProductModal() {
        uiState = uiState.copy(showAddProductModal = false, addProductErrorMessage = null)
    }
    
    /**
     * Agrega un nuevo producto
     */
    fun addProduct(
        name: String,
        description: String?,
        purchasePrice: Double?,
        salePrice: Double?,
        internalNotes: String?
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(isAddingProduct = true, addProductErrorMessage = null)
            
            // Crear el producto con los datos proporcionados
            val newProduct = Product(
                id = null, // Se asignará en el servidor
                name = name,
                description = description,
                purchasePrice = purchasePrice,
                salePrice = salePrice,
                internalNotes = internalNotes,
                categoryId = null,
                categoryName = null,
                unitId = null,
                unitName = null,
                unitAbbreviation = null,
                tags = null
            )
            
            // TODO: Implementar la llamada al repositorio para crear el producto
            // Por ahora simulamos una operación exitosa
            try {
                // Simular delay de red
                kotlinx.coroutines.delay(1000)
                
                // Agregar el producto a la lista local
                val updatedProducts = uiState.products + newProduct
                uiState = uiState.copy(
                    products = updatedProducts,
                    filteredProducts = updatedProducts,
                    isAddingProduct = false,
                    showAddProductModal = false
                )
                
                // Actualizar la búsqueda si hay una consulta activa
                if (uiState.searchQuery.isNotBlank()) {
                    filterProducts(uiState.searchQuery)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isAddingProduct = false,
                    addProductErrorMessage = e.message ?: "Error al agregar el producto"
                )
            }
        }
    }
    
    /**
     * Limpia el mensaje de error del modal de agregar producto
     */
    fun clearAddProductError() {
        uiState = uiState.copy(addProductErrorMessage = null)
    }

    /**
     * Muestra el modal de detalle de producto
     */
    fun showProductDetailModal(product: Product) {
        uiState = uiState.copy(
            showProductDetailModal = true,
            selectedProduct = product
        )
    }

    /**
     * Oculta el modal de detalle de producto
     */
    fun hideProductDetailModal() {
        uiState = uiState.copy(
            showProductDetailModal = false,
            selectedProduct = null
        )
    }

    /**
     * Muestra el modal de editar producto
     */
    fun showEditProductModal(product: Product) {
        uiState = uiState.copy(
            showEditProductModal = true,
            editingProduct = product,
            updateProductErrorMessage = null
        )
        hideProductDetailModal()
    }

    /**
     * Oculta el modal de editar producto
     */
    fun hideEditProductModal() {
        uiState = uiState.copy(
            showEditProductModal = false,
            editingProduct = null,
            updateProductErrorMessage = null
        )
    }

    /**
     * Actualiza un producto existente
     */
    fun updateProduct(
        id: Int,
        name: String,
        description: String?,
        purchasePrice: Double?,
        salePrice: Double?,
        internalNotes: String?
    ) {
        val currentEditingProduct = uiState.editingProduct ?: return

        viewModelScope.launch {
            uiState = uiState.copy(isUpdatingProduct = true, updateProductErrorMessage = null)

            // Crear el producto actualizado
            val updatedProduct = currentEditingProduct.copy(
                name = name,
                description = description,
                purchasePrice = purchasePrice,
                salePrice = salePrice,
                internalNotes = internalNotes
            )

            // TODO: Implementar la llamada al repositorio para actualizar el producto
            // Por ahora simulamos una operación exitosa
            try {
                // Simular delay de red
                kotlinx.coroutines.delay(1000)

                // Actualizar el producto en la lista local
                val updatedProducts = uiState.products.map { product ->
                    if (product.id == id) updatedProduct else product
                }
                uiState = uiState.copy(
                    products = updatedProducts,
                    filteredProducts = updatedProducts,
                    isUpdatingProduct = false,
                    showEditProductModal = false,
                    editingProduct = null
                )

                // Actualizar la búsqueda si hay una consulta activa
                if (uiState.searchQuery.isNotBlank()) {
                    filterProducts(uiState.searchQuery)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isUpdatingProduct = false,
                    updateProductErrorMessage = e.message ?: "Error al actualizar el producto"
                )
            }
        }
    }

    /**
     * Limpia el mensaje de error del modal de editar producto
     */
    fun clearUpdateProductError() {
        uiState = uiState.copy(updateProductErrorMessage = null)
    }

    /**
     * Maneja la acción de editar producto desde el modal de detalle
     */
    fun onEditProduct(product: Product) {
        showEditProductModal(product)
    }
}