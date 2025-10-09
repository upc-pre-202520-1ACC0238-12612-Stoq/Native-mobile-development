package com.stoq.StockWise.product.domain.entities

/**
 * Entidad de dominio para representar un producto
 */
data class Product(
    val id: Int?,
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
    val tags: List<Tag>?
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
}

/**
 * Entidad de dominio para representar un tag
 */
data class Tag(
    val id: Int?,
    val name: String?
)