package com.stoq.StockWise.inventory.infrastructure.api

import com.stoq.StockWise.inventory.infrastructure.dto.InventoryDto
import retrofit2.Response
import retrofit2.http.*

/**
 * Servicio API para la gestión de inventarios
 * 
 * Define los endpoints REST para las operaciones CRUD
 * de inventarios en el servidor.
 */
interface InventoryApiService {
    
    /**
     * Obtiene todos los inventarios del usuario autenticado
     * 
     * @return Response con la lista de inventarios
     */
    @GET("inventories")
    suspend fun getAllInventories(): Response<List<InventoryDto>>
    
    /**
     * Obtiene un inventario específico por su ID
     * 
     * @param id ID del inventario
     * @return Response con el inventario
     */
    @GET("inventories/{id}")
    suspend fun getInventoryById(@Path("id") id: Int): Response<InventoryDto>
    
    /**
     * Obtiene el inventario principal del usuario
     * 
     * @return Response con el inventario principal
     */
    @GET("inventories/main")
    suspend fun getMainInventory(): Response<InventoryDto>
    
    /**
     * Verifica si el usuario tiene al menos un inventario
     * 
     * @param userId ID del usuario a verificar
     * @return Response con true si tiene inventario, false si no
     */
    @GET("inventories/has-inventory/{userId}")
    suspend fun hasInventory(@Path("userId") userId: Int): Response<Boolean>
    
    /**
     * Crea un nuevo inventario
     * 
     * @param inventory Datos del inventario a crear
     * @return Response con el inventario creado
     */
    @POST("inventories")
    suspend fun createInventory(@Body inventory: InventoryDto): Response<InventoryDto>
    
    /**
     * Actualiza un inventario existente
     * 
     * @param id ID del inventario a actualizar
     * @param inventory Datos actualizados del inventario
     * @return Response con el inventario actualizado
     */
    @PUT("inventories/{id}")
    suspend fun updateInventory(
        @Path("id") id: Int,
        @Body inventory: InventoryDto
    ): Response<InventoryDto>
    
    /**
     * Elimina un inventario
     * 
     * @param id ID del inventario a eliminar
     * @return Response vacío indicando éxito
     */
    @DELETE("inventories/{id}")
    suspend fun deleteInventory(@Path("id") id: Int): Response<Unit>
}
