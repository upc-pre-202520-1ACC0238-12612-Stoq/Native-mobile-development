package com.stoq.StockWise.shared.domain.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * EventBus global para comunicación entre bounded contexts.
 * Implementa el patrón Observer para eventos de dominio.
 */
object EventBus {
    
    private val _events = MutableSharedFlow<DomainEvent>()
    val events: SharedFlow<DomainEvent> = _events.asSharedFlow()
    
    /**
     * Emite un evento de dominio
     * @param event El evento a emitir
     */
    suspend fun emit(event: DomainEvent) {
        _events.emit(event)
    }
}

/**
 * Interfaz base para todos los eventos de dominio
 */
sealed interface DomainEvent

/**
 * Evento emitido cuando el login es exitoso
 * @param userId ID del usuario autenticado
 * @param token Token JWT del usuario
 */
data class LoginSuccessEvent(
    val userId: Int,
    val token: String
) : DomainEvent

/**
 * Evento emitido cuando el usuario se registra exitosamente
 * @param userId ID del usuario registrado
 * @param token Token JWT del usuario
 */
data class RegisterSuccessEvent(
    val userId: Int,
    val token: String
) : DomainEvent

/**
 * Evento emitido cuando el inventario es creado
 * @param inventoryId ID del inventario creado
 * @param userId ID del propietario del inventario
 */
data class InventoryCreatedEvent(
    val inventoryId: Int,
    val userId: Int
) : DomainEvent

/**
 * Evento emitido cuando el primer producto es creado
 * @param productId ID del producto creado
 * @param inventoryId ID del inventario
 */
data class FirstProductCreatedEvent(
    val productId: Int,
    val inventoryId: Int
) : DomainEvent

