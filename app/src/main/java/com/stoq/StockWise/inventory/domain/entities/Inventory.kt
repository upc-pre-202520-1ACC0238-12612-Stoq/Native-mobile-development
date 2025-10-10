package com.stoq.StockWise.inventory.domain.entities

/**
 * Agregado principal que representa un inventario en el sistema
 * 
 * Un inventario es la colección de productos que posee un usuario
 * y representa el contexto principal para la gestión de stock.
 */
data class Inventory(
    val id: Int,
    val name: String,
    val description: String?,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String,
    val isActive: Boolean = true
) {
    /**
     * Verifica si el inventario está activo y puede ser utilizado
     */
    fun isUsable(): Boolean = isActive
    
    /**
     * Obtiene el nombre para mostrar en la UI
     */
    fun getDisplayName(): String = name.ifBlank { "Inventario sin nombre" }
}