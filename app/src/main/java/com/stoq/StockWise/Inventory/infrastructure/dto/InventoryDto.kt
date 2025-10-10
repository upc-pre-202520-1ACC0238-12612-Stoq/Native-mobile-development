package com.stoq.StockWise.inventory.infrastructure.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object para la entidad Inventory
 * 
 * Representa la estructura de datos tal como viene del API REST
 * y se mapea a la entidad de dominio correspondiente.
 */
data class InventoryDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("user_id")
    val userId: Int,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("updated_at")
    val updatedAt: String,
    
    @SerializedName("is_active")
    val isActive: Boolean = true
)
