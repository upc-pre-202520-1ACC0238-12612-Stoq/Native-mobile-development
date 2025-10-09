package com.stoq.StockWise.inventory.domain.entities

/**
 * Entidad de dominio para representar un producto dentro de un inventario.
 * Pertenece al agregado Inventory.
 * 
 * @param id Identificador único del producto
 * @param inventoryId ID del inventario al que pertenece
 * @param name Nombre del producto
 * @param description Descripción del producto
 * @param purchasePrice Precio de compra
 * @param salePrice Precio de venta
 * @param internalNotes Notas internas del producto
 * @param categoryId ID de la categoría
 * @param categoryName Nombre de la categoría
 * @param unitId ID de la unidad de medida
 * @param unitName Nombre de la unidad de medida
 * @param unitAbbreviation Abreviación de la unidad
 * @param tags Lista de tags asociados al producto
 * @param stock Cantidad en stock
 * @param minStock Stock mínimo requerido
 * @param isActive Indica si el producto está activo
 * @param createdAt Fecha de creación
 * @param updatedAt Fecha de última actualización
 */
data class Product(
    val id: Int? = null,
    val inventoryId: Int,
    val name: String?,
    val description: String?,
    val purchasePrice: Double?,
    val salePrice: Double?,
    val internalNotes: String?,
    val categoryId: Int?,
    val categoryName: String?,
    val unitId: Int?,
    val unitName: String?,
    val unitAbbreviation: String?,
    val tags: List<Tag>? = null,
    val stock: Int = 0,
    val minStock: Int = 0,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    /**
     * Calcula el margen de ganancia del producto
     */
    fun getProfit(): Double {
        val purchase = purchasePrice ?: 0.0
        val sale = salePrice ?: 0.0
        return sale - purchase
    }
    
    /**
     * Calcula el porcentaje de margen de ganancia
     */
    fun getProfitPercentage(): Double {
        val purchase = purchasePrice ?: 0.0
        val sale = salePrice ?: 0.0
        return if (purchase > 0) {
            ((sale - purchase) / purchase) * 100
        } else {
            0.0
        }
    }
    
    /**
     * Verifica si el producto tiene stock bajo
     */
    fun isLowStock(): Boolean {
        return stock <= minStock
    }
    
    /**
     * Verifica si el producto está sin stock
     */
    fun isOutOfStock(): Boolean {
        return stock <= 0
    }
    
    /**
     * Obtiene el estado del stock como texto
     */
    fun getStockStatus(): String {
        return when {
            isOutOfStock() -> "Sin stock"
            isLowStock() -> "Stock bajo"
            else -> "En stock"
        }
    }
    
    /**
     * Valida que el producto tenga datos básicos válidos
     */
    fun isValid(): Boolean {
        return !name.isNullOrBlank() && inventoryId > 0
    }
}

/**
 * Entidad de dominio para representar un tag
 */
data class Tag(
    val id: Int? = null,
    val name: String?
) {
    /**
     * Valida que el tag tenga un nombre válido
     */
    fun isValid(): Boolean {
        return !name.isNullOrBlank()
    }
}

