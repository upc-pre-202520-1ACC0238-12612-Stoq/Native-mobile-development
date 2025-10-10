package com.stoq.StockWise.inventory.domain.services

import com.stoq.StockWise.inventory.domain.entities.Inventory
import com.stoq.StockWise.inventory.domain.repositories.InventoryRepository
import com.stoq.StockWise.shared.domain.events.EventBus
import com.stoq.StockWise.shared.domain.events.InventoryCreatedEvent

/**
 * Servicio de dominio para la lógica de negocio relacionada con inventarios.
 * Maneja la verificación y creación de inventarios para usuarios.
 */
class InventoryDomainService(
    private val inventoryRepository: InventoryRepository
) {
    
    /**
     * Verifica si un usuario tiene inventario y lo crea si no existe.
     * @param userId ID del usuario
     * @return Result con el inventario del usuario (existente o creado)
     */
    suspend fun ensureUserHasInventory(userId: Int): Result<Inventory> {
        return try {
            // Verificar si el usuario ya tiene inventario
            val hasInventoryResult = inventoryRepository.hasInventory(userId)
            
            if (hasInventoryResult.isSuccess && hasInventoryResult.getOrNull() == true) {
                // Usuario ya tiene inventario, obtener el principal
                inventoryRepository.getMainInventory()
            } else {
                // Usuario no tiene inventario, crear uno por defecto
                createDefaultInventory(userId)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Crea un inventario por defecto para un usuario nuevo.
     * @param userId ID del usuario
     * @return Result con el inventario creado
     */
    private suspend fun createDefaultInventory(userId: Int): Result<Inventory> {
        val currentTime = System.currentTimeMillis().toString()
        val defaultInventory = Inventory(
            id = 0, // Se asignará el ID real en el repositorio
            userId = userId,
            name = "Mi Inventario",
            description = "Inventario principal creado automáticamente",
            createdAt = currentTime,
            updatedAt = currentTime,
            isActive = true
        )
        
        return inventoryRepository.createInventory(defaultInventory)
            .onSuccess { inventory ->
                // Emitir evento de inventario creado
                EventBus.emit(InventoryCreatedEvent(inventory.id ?: 0, userId))
            }
    }
    
    /**
     * Verifica si un usuario necesita configurar su inventario.
     * @param userId ID del usuario
     * @return true si necesita configuración, false si ya está configurado
     */
    suspend fun needsInventorySetup(userId: Int): Result<Boolean> {
        return inventoryRepository.hasInventory(userId)
            .map { hasInventory -> !hasInventory }
    }
}

