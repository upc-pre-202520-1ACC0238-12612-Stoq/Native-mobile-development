package com.stoq.StockWise.ProductCatalog.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.ProductCatalog.domain.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val searchTerm: String = "",
    val selectedCategory: String = "",
    val errorMessage: String? = null
)

data class ProductFormState(
    val name: String = "",
    val tags: String = "",
    val buyPrice: String = "",
    val sellPrice: String = "",
    val quantity: String = "",
    val lot: String = "",
    val expiryDate: String = "",
    val note: String = "",
    val isEditing: Boolean = false,
    val editingProduct: Product? = null
)

data class ProductEditState(
    val product: Product? = null,
    val quantity: String = "",
    val expiryDate: String = "",
    val notes: String = "",
    val showEditModal: Boolean = false,
    val selectedTags: Set<String> = emptySet()
)

class ProductCatalogViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState

    private val _formState = MutableStateFlow(ProductFormState())
    val formState: StateFlow<ProductFormState> = _formState

    private val _editState = MutableStateFlow(ProductEditState())
    val editState: StateFlow<ProductEditState> = _editState

    init {
        loadMockProducts()
    }

    private fun loadMockProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Mock data for visual demonstration
            val mockProducts = listOf(
                Product(
                    id = "1",
                    name = "Galleta",
                    description = "Galletas rellenas de chocolate",
                    price = 2.50,
                    categoryId = "golosina",
                    sku = "GAL001",
                    stockQuantity = 20,
                    minimumStock = 5,
                    tags = listOf("Dulce", "Rellenas")
                ),
                Product(
                    id = "2",
                    name = "Galleta",
                    description = "Galletas de vainilla",
                    price = 2.00,
                    categoryId = "golosina",
                    sku = "GAL002",
                    stockQuantity = 15,
                    minimumStock = 5,
                    tags = listOf("Dulce", "Vainilla")
                ),
                Product(
                    id = "3",
                    name = "Galleta",
                    description = "Galletas con chispas",
                    price = 3.00,
                    categoryId = "golosina",
                    sku = "GAL003",
                    stockQuantity = 25,
                    minimumStock = 5,
                    tags = listOf("Dulce", "Chispas")
                ),
                Product(
                    id = "4",
                    name = "Galleta",
                    description = "Galletas integrales",
                    price = 2.75,
                    categoryId = "golosina",
                    sku = "GAL004",
                    stockQuantity = 18,
                    minimumStock = 5,
                    tags = listOf("Saludable", "Integral")
                )
            )

            _uiState.value = _uiState.value.copy(
                products = mockProducts,
                isLoading = false
            )
        }
    }

    fun updateSearchTerm(term: String) {
        _uiState.value = _uiState.value.copy(searchTerm = term)
        filterProducts()
    }

    fun updateSelectedCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
        filterProducts()
    }

    private fun filterProducts() {
        val currentState = _uiState.value
        val filtered = if (currentState.searchTerm.isBlank() && currentState.selectedCategory.isBlank()) {
            // Return all mock products when no filters
            listOf(
                Product(
                    id = "1",
                    name = "Galleta",
                    description = "Galletas rellenas de chocolate",
                    price = 2.50,
                    categoryId = "golosina",
                    sku = "GAL001",
                    stockQuantity = 20,
                    minimumStock = 5,
                    tags = listOf("Dulce", "Rellenas")
                ),
                Product(
                    id = "2",
                    name = "Galleta",
                    description = "Galletas de vainilla",
                    price = 2.00,
                    categoryId = "golosina",
                    sku = "GAL002",
                    stockQuantity = 15,
                    minimumStock = 5,
                    tags = listOf("Dulce", "Vainilla")
                ),
                Product(
                    id = "3",
                    name = "Galleta",
                    description = "Galletas con chispas",
                    price = 3.00,
                    categoryId = "golosina",
                    sku = "GAL003",
                    stockQuantity = 25,
                    minimumStock = 5,
                    tags = listOf("Dulce", "Chispas")
                ),
                Product(
                    id = "4",
                    name = "Galleta",
                    description = "Galletas integrales",
                    price = 2.75,
                    categoryId = "golosina",
                    sku = "GAL004",
                    stockQuantity = 18,
                    minimumStock = 5,
                    tags = listOf("Saludable", "Integral")
                )
            )
        } else {
            emptyList() // For now, return empty when filters are applied
        }

        _uiState.value = currentState.copy(products = filtered)
    }

    // Form operations
    fun updateFormName(name: String) {
        _formState.value = _formState.value.copy(name = name)
    }

    fun updateFormTags(tags: String) {
        _formState.value = _formState.value.copy(tags = tags)
    }

    fun updateFormBuyPrice(price: String) {
        _formState.value = _formState.value.copy(buyPrice = price)
    }

    fun updateFormSellPrice(price: String) {
        _formState.value = _formState.value.copy(sellPrice = price)
    }

    fun updateFormQuantity(quantity: String) {
        _formState.value = _formState.value.copy(quantity = quantity)
    }

    fun updateFormLot(lot: String) {
        _formState.value = _formState.value.copy(lot = lot)
    }

    fun updateFormExpiryDate(date: String) {
        _formState.value = _formState.value.copy(expiryDate = date)
    }

    fun updateFormNote(note: String) {
        _formState.value = _formState.value.copy(note = note)
    }

    fun clearForm() {
        _formState.value = ProductFormState()
    }

    fun saveProduct() {
        viewModelScope.launch {
            val formState = _formState.value

            // Validation
            if (formState.name.isBlank()) {
                _uiState.value = _uiState.value.copy(errorMessage = "El nombre del producto es requerido")
                return@launch
            }

            if (formState.buyPrice.isBlank() || formState.sellPrice.isBlank()) {
                _uiState.value = _uiState.value.copy(errorMessage = "Los precios son requeridos")
                return@launch
            }

            if (formState.quantity.isBlank()) {
                _uiState.value = _uiState.value.copy(errorMessage = "La cantidad es requerida")
                return@launch
            }

            // For now, just clear the form and show success
            clearForm()
            _uiState.value = _uiState.value.copy(errorMessage = null)

            // In a real implementation, this would save to the repository
            loadMockProducts() // Reload products to show the new one
        }
    }

    // Edit modal operations
    fun openEditModal(product: Product) {
        _editState.value = ProductEditState(
            product = product,
            quantity = product.stockQuantity.toString(),
            expiryDate = "",
            notes = "",
            showEditModal = true,
            selectedTags = product.tags.toSet()
        )
    }

    fun closeEditModal() {
        _editState.value = ProductEditState(showEditModal = false)
    }

    fun updateEditQuantity(quantity: String) {
        _editState.value = _editState.value.copy(quantity = quantity)
    }

    fun updateEditExpiryDate(date: String) {
        _editState.value = _editState.value.copy(expiryDate = date)
    }

    fun updateEditNotes(notes: String) {
        _editState.value = _editState.value.copy(notes = notes)
    }

    fun toggleEditTag(tag: String) {
        val currentTags = _editState.value.selectedTags.toMutableSet()
        if (currentTags.contains(tag)) {
            currentTags.remove(tag)
        } else {
            currentTags.add(tag)
        }
        _editState.value = _editState.value.copy(selectedTags = currentTags)
    }

    fun duplicateProduct() {
        viewModelScope.launch {
            val product = _editState.value.product
            if (product != null) {
                // In a real implementation, this would duplicate the product
                closeEditModal()
                loadMockProducts() // Reload to show the duplicated product
            }
        }
    }

    fun deleteProduct() {
        viewModelScope.launch {
            val product = _editState.value.product
            if (product != null) {
                // In a real implementation, this would delete the product
                closeEditModal()
                loadMockProducts() // Reload to show the updated list
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}