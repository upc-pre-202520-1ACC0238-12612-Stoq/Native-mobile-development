package com.stoq.StockWise.inventory.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.inventory.domain.entities.Inventory
import com.stoq.StockWise.inventory.domain.repositories.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la configuración inicial del inventario
 * 
 * Maneja el estado y la lógica para la creación del primer inventario
 * y la configuración inicial del usuario.
 */
class InventorySetupViewModel(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InventorySetupUiState())
    val uiState: StateFlow<InventorySetupUiState> = _uiState.asStateFlow()
    
    init {
        checkInventorySetupStatus()
    }
    
    /**
     * Verifica el estado actual de configuración del inventario
     */
    private fun checkInventorySetupStatus() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            inventoryRepository.getMainInventory()
                .onSuccess { inventory ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        needsInventorySetup = inventory == null,
                        needsFirstProduct = inventory != null && inventory.isUsable(),
                        isReady = inventory != null && inventory.isUsable(),
                        mainInventory = inventory
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error desconocido",
                        needsInventorySetup = true
                    )
                }
        }
    }
    
    /**
     * Crea un nuevo inventario principal
     * 
     * @param name Nombre del inventario
     * @param description Descripción opcional del inventario
     */
    fun createMainInventory(name: String, description: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            val newInventory = Inventory(
                id = 0, // Será asignado por el servidor
                name = name,
                description = description,
                userId = 1, // TODO: Obtener del usuario autenticado
                createdAt = "", // Será asignado por el servidor
                updatedAt = "" // Será asignado por el servidor
            )
            
            inventoryRepository.createInventory(newInventory)
                .onSuccess { createdInventory ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        needsInventorySetup = false,
                        needsFirstProduct = true,
                        isReady = false,
                        mainInventory = createdInventory
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Error al crear inventario"
                    )
                }
        }
    }
    
    /**
     * Completa la configuración del primer producto
     * Marca que el usuario ya no necesita crear el primer producto
     */
    fun completeFirstProductSetup() {
        _uiState.value = _uiState.value.copy(
            needsFirstProduct = false,
            isReady = true
        )
    }
    
    /**
     * Limpia el mensaje de error
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

/**
 * Estado de la UI para la configuración del inventario
 */
data class InventorySetupUiState(
    val isLoading: Boolean = false,
    val needsInventorySetup: Boolean = true,
    val needsFirstProduct: Boolean = false,
    val isReady: Boolean = false,
    val mainInventory: Inventory? = null,
    val errorMessage: String? = null
)