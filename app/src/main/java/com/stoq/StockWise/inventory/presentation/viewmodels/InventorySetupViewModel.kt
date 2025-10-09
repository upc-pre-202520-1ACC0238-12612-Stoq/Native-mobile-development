package com.stoq.StockWise.inventory.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.inventory.domain.entities.Inventory
import com.stoq.StockWise.inventory.domain.services.InventoryDomainService
import com.stoq.StockWise.shared.domain.events.EventBus
import com.stoq.StockWise.shared.domain.events.LoginSuccessEvent
import com.stoq.StockWise.shared.domain.events.RegisterSuccessEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para manejar el flujo de configuración inicial del inventario.
 * Escucha eventos de login/register y verifica si el usuario necesita configuración.
 */
class InventorySetupViewModel(
    private val inventoryDomainService: InventoryDomainService
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InventorySetupUiState())
    val uiState: StateFlow<InventorySetupUiState> = _uiState.asStateFlow()
    
    init {
        // Escuchar eventos de autenticación
        viewModelScope.launch {
            EventBus.events.collect { event ->
                when (event) {
                    is LoginSuccessEvent -> handleLoginSuccess(event.userId)
                    is RegisterSuccessEvent -> handleRegisterSuccess(event.userId)
                    else -> { /* Otros eventos no relevantes */ }
                }
            }
        }
    }
    
    /**
     * Maneja el evento de login exitoso
     */
    private fun handleLoginSuccess(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            inventoryDomainService.ensureUserHasInventory(userId)
                .onSuccess { inventory ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        currentUserId = userId,
                        userInventory = inventory,
                        needsInventorySetup = false,
                        needsFirstProduct = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error al verificar inventario"
                    )
                }
        }
    }
    
    /**
     * Maneja el evento de registro exitoso
     */
    private fun handleRegisterSuccess(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            inventoryDomainService.needsInventorySetup(userId)
                .onSuccess { needsSetup ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        currentUserId = userId,
                        needsInventorySetup = needsSetup,
                        needsFirstProduct = needsSetup
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error al verificar configuración"
                    )
                }
        }
    }
    
    /**
     * Crea un inventario para el usuario
     */
    fun createInventory(name: String, description: String) {
        val userId = _uiState.value.currentUserId
        if (userId == null) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Usuario no autenticado"
            )
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val inventory = Inventory(
                userId = userId,
                name = name,
                description = description.ifEmpty { null }
            )
            
            inventoryDomainService.ensureUserHasInventory(userId)
                .onSuccess { createdInventory ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userInventory = createdInventory,
                        needsInventorySetup = false,
                        needsFirstProduct = true
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error al crear inventario"
                    )
                }
        }
    }
    
    /**
     * Marca que el primer producto ha sido creado o saltado
     */
    fun completeFirstProductSetup() {
        _uiState.value = _uiState.value.copy(
            needsFirstProduct = false
        )
    }
    
    /**
     * Limpia el estado de error
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
    val currentUserId: Int? = null,
    val userInventory: Inventory? = null,
    val needsInventorySetup: Boolean = false,
    val needsFirstProduct: Boolean = false,
    val errorMessage: String? = null
) {
    /**
     * Indica si el usuario está listo para usar la aplicación
     */
    val isReady: Boolean
        get() = !isLoading && 
                currentUserId != null && 
                userInventory != null && 
                !needsInventorySetup && 
                !needsFirstProduct
}

