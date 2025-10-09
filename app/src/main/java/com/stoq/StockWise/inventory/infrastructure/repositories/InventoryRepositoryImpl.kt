package com.stoq.StockWise.inventory.infrastructure.repositories

import com.stoq.StockWise.inventory.domain.entities.Inventory
import com.stoq.StockWise.inventory.domain.repositories.InventoryRepository
import kotlinx.coroutines.delay

/**
 * Implementación local del repositorio de inventarios.
 * Por ahora almacena datos en memoria hasta que se implemente la API.
 */
class InventoryRepositoryImpl : InventoryRepository {
    
    // Almacenamiento temporal en memoria
    private val inventories = mutableListOf<Inventory>()
    private var nextId = 1
    
    override suspend fun getInventoriesByUserId(userId: Int): Result<List<Inventory>> {
        return try {
            delay(500) // Simular latencia de red
            val userInventories = inventories.filter { it.userId == userId }
            Result.success(userInventories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getInventoryById(inventoryId: Int): Result<Inventory> {
        return try {
            delay(300) // Simular latencia de red
            val inventory = inventories.find { it.id == inventoryId }
            if (inventory != null) {
                Result.success(inventory)
            } else {
                Result.failure(Exception("Inventario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun createInventory(inventory: Inventory): Result<Inventory> {
        return try {
            delay(800) // Simular latencia de red
            val newInventory = inventory.copy(id = nextId++)
            inventories.add(newInventory)
            Result.success(newInventory)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateInventory(inventory: Inventory): Result<Inventory> {
        return try {
            delay(600) // Simular latencia de red
            val index = inventories.indexOfFirst { it.id == inventory.id }
            if (index != -1) {
                inventories[index] = inventory
                Result.success(inventory)
            } else {
                Result.failure(Exception("Inventario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteInventory(inventoryId: Int): Result<Unit> {
        return try {
            delay(400) // Simular latencia de red
            val removed = inventories.removeAll { it.id == inventoryId }
            if (removed) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Inventario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun hasInventory(userId: Int): Result<Boolean> {
        return try {
            delay(200) // Simular latencia de red
            val hasInventory = inventories.any { it.userId == userId }
            Result.success(hasInventory)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getMainInventory(userId: Int): Result<Inventory> {
        return try {
            delay(300) // Simular latencia de red
            val mainInventory = inventories
                .filter { it.userId == userId }
                .minByOrNull { it.id ?: Int.MAX_VALUE }
            
            if (mainInventory != null) {
                Result.success(mainInventory)
            } else {
                Result.failure(Exception("Usuario no tiene inventarios"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

