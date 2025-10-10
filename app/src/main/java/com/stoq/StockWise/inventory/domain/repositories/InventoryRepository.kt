package com.stoq.StockWise.inventory.domain.repositories

import com.stoq.StockWise.inventory.domain.entities.Inventory

/**
 * Contrato del repositorio para la gestión de inventarios
 * 
 * Define las operaciones que pueden realizarse sobre los inventarios
 * sin depender de la implementación específica de persistencia.
 */
interface InventoryRepository {
    
    /**
     * Obtiene todos los inventarios del usuario autenticado
     * 
     * @return Result que contiene la lista de inventarios o un error
     */
    suspend fun getAllInventories(): Result<List<Inventory>>
    
    /**
     * Obtiene un inventario específico por su ID
     * 
     * @param id ID del inventario a buscar
     * @return Result que contiene el inventario o un error
     */
    suspend fun getInventoryById(id: Int): Result<Inventory>
    
    /**
     * Obtiene el inventario principal del usuario autenticado
     * 
     * @return Result que contiene el inventario principal o un error
     */
    suspend fun getMainInventory(): Result<Inventory>
    
    /**
     * Verifica si el usuario autenticado tiene al menos un inventario
     * 
     * @param userId ID del usuario a verificar
     * @return Result que indica si el usuario tiene inventario
     */
    suspend fun hasInventory(userId: Int): Result<Boolean>
    
    /**
     * Crea un nuevo inventario
     * 
     * @param inventory Datos del inventario a crear
     * @return Result que contiene el inventario creado o un error
     */
    suspend fun createInventory(inventory: Inventory): Result<Inventory>
    
    /**
     * Actualiza un inventario existente
     * 
     * @param inventory Inventario con los datos actualizados
     * @return Result que contiene el inventario actualizado o un error
     */
    suspend fun updateInventory(inventory: Inventory): Result<Inventory>
    
    /**
     * Elimina un inventario
     * 
     * @param id ID del inventario a eliminar
     * @return Result que indica si la operación fue exitosa
     */
    suspend fun deleteInventory(id: Int): Result<Unit>
}