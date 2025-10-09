package com.stoq.StockWise.inventory.domain.entities

/**
 * Agregado principal del bounded context Inventory.
 * Representa un inventario que pertenece a un usuario y contiene productos.
 * 
 * @param id Identificador único del inventario
 * @param userId ID del propietario del inventario
 * @param name Nombre del inventario
 * @param description Descripción opcional del inventario
 * @param isActive Indica si el inventario está activo
 * @param createdAt Fecha de creación del inventario
 * @param updatedAt Fecha de última actualización
 */
data class Inventory(
    val id: Int? = null,
    val userId: Int,
    val name: String,
    val description: String? = null,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    /**
     * Valida que el inventario tenga un nombre válido
     */
    fun isValid(): Boolean {
        return name.isNotBlank() && userId > 0
    }
    
    /**
     * Obtiene el nombre para mostrar en la UI
     */
    fun getDisplayName(): String {
        return name.ifEmpty { "Mi Inventario" }
    }
}

