package com.stoq.StockWise.inventory.infrastructure.repositories

import com.stoq.StockWise.inventory.domain.entities.Inventory
import com.stoq.StockWise.inventory.domain.repositories.InventoryRepository
import com.stoq.StockWise.inventory.infrastructure.api.InventoryApiService
import com.stoq.StockWise.inventory.infrastructure.mappers.InventoryMapper

/**
 * Implementación del repositorio de inventarios
 * 
 * Proporciona la implementación concreta del contrato InventoryRepository
 * utilizando el servicio API para la persistencia de datos.
 */
class InventoryRepositoryImpl(
    private val apiService: InventoryApiService
) : InventoryRepository {
    
    override suspend fun getAllInventories(): Result<List<Inventory>> {
        return try {
            val response = apiService.getAllInventories()
            if (response.isSuccessful) {
                val inventories = response.body()?.let { InventoryMapper.fromDtoList(it) } ?: emptyList()
                Result.success(inventories)
            } else {
                Result.failure(Exception("Error al obtener inventarios: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getInventoryById(id: Int): Result<Inventory> {
        return try {
            val response = apiService.getInventoryById(id)
            if (response.isSuccessful) {
                val inventoryDto = response.body()
                if (inventoryDto != null) {
                    Result.success(InventoryMapper.fromDto(inventoryDto))
                } else {
                    Result.failure(Exception("Inventario no encontrado"))
                }
            } else {
                Result.failure(Exception("Error al obtener inventario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getMainInventory(): Result<Inventory> {
        return try {
            val response = apiService.getMainInventory()
            if (response.isSuccessful) {
                val inventoryDto = response.body()
                if (inventoryDto != null) {
                    Result.success(InventoryMapper.fromDto(inventoryDto))
                } else {
                    Result.failure(Exception("No se encontró inventario principal"))
                }
            } else {
                Result.failure(Exception("Error al obtener inventario principal: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun hasInventory(userId: Int): Result<Boolean> {
        return try {
            val response = apiService.hasInventory(userId)
            if (response.isSuccessful) {
                val hasInventory = response.body() ?: false
                Result.success(hasInventory)
            } else {
                Result.failure(Exception("Error al verificar inventario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun createInventory(inventory: Inventory): Result<Inventory> {
        return try {
            val inventoryDto = InventoryMapper.toDto(inventory)
            val response = apiService.createInventory(inventoryDto)
            if (response.isSuccessful) {
                val createdInventoryDto = response.body()
                if (createdInventoryDto != null) {
                    Result.success(InventoryMapper.fromDto(createdInventoryDto))
                } else {
                    Result.failure(Exception("Error al crear inventario: respuesta vacía"))
                }
            } else {
                Result.failure(Exception("Error al crear inventario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateInventory(inventory: Inventory): Result<Inventory> {
        return try {
            val inventoryDto = InventoryMapper.toDto(inventory)
            val response = apiService.updateInventory(inventory.id, inventoryDto)
            if (response.isSuccessful) {
                val updatedInventoryDto = response.body()
                if (updatedInventoryDto != null) {
                    Result.success(InventoryMapper.fromDto(updatedInventoryDto))
                } else {
                    Result.failure(Exception("Error al actualizar inventario: respuesta vacía"))
                }
            } else {
                Result.failure(Exception("Error al actualizar inventario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteInventory(id: Int): Result<Unit> {
        return try {
            val response = apiService.deleteInventory(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar inventario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}