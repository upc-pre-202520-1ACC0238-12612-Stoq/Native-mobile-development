package com.stoq.StockWise.inventory.domain.repositories

import com.stoq.StockWise.inventory.domain.entities.Inventory

/**
 * Contrato de repositorio para la entidad Inventory.
 * Define las operaciones de acceso a datos para inventarios.
 */
interface InventoryRepository {
    
    /**
     * Obtiene todos los inventarios de un usuario
     * @param userId ID del usuario
     * @return Result con la lista de inventarios
     */
    suspend fun getInventoriesByUserId(userId: Int): Result<List<Inventory>>
    
    /**
     * Obtiene un inventario por su ID
     * @param inventoryId ID del inventario
     * @return Result con el inventario o error
     */
    suspend fun getInventoryById(inventoryId: Int): Result<Inventory>
    
    /**
     * Crea un nuevo inventario
     * @param inventory Datos del inventario a crear
     * @return Result con el inventario creado
     */
    suspend fun createInventory(inventory: Inventory): Result<Inventory>
    
    /**
     * Actualiza un inventario existente
     * @param inventory Datos del inventario a actualizar
     * @return Result con el inventario actualizado
     */
    suspend fun updateInventory(inventory: Inventory): Result<Inventory>
    
    /**
     * Elimina un inventario
     * @param inventoryId ID del inventario a eliminar
     * @return Result indicando si la operación fue exitosa
     */
    suspend fun deleteInventory(inventoryId: Int): Result<Unit>
    
    /**
     * Verifica si un usuario tiene al menos un inventario
     * @param userId ID del usuario
     * @return Result con true si tiene inventario, false si no
     */
    suspend fun hasInventory(userId: Int): Result<Boolean>
    
    /**
     * Obtiene el inventario principal de un usuario (el primero creado)
     * @param userId ID del usuario
     * @return Result con el inventario principal o error
     */
    suspend fun getMainInventory(userId: Int): Result<Inventory>
}

