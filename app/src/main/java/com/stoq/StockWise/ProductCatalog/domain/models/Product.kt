package com.stoq.StockWise.ProductCatalog.domain.models

import kotlinx.serialization.Serializable

/**
 * Product domain model following DDD principles.
 * This is a rich domain model that represents a Product in the system.
 */
@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val categoryId: String,
    val sku: String,
    val isActive: Boolean = true,
    val stockQuantity: Int = 0,
    val minimumStock: Int = 0,
    val imageUrl: String? = null,
    val tags: List<String> = emptyList()
) {
    
    /**
     * Business rule: Product price must be positive
     */
    init {
        require(price > 0) { "Product price must be positive" }
        require(name.isNotBlank()) { "Product name cannot be blank" }
        require(sku.isNotBlank()) { "Product SKU cannot be blank" }
    }
    
    /**
     * Check if product is in stock
     */
    fun isInStock(): Boolean = stockQuantity > 0
    
    /**
     * Check if product needs restocking
     */
    fun needsRestocking(): Boolean = stockQuantity <= minimumStock
    
    /**
     * Check if product is available for sale
     */
    fun isAvailableForSale(): Boolean = isActive && isInStock()
    
    /**
     * Create a copy with updated stock
     */
    fun withStock(newStockQuantity: Int): Product {
        require(newStockQuantity >= 0) { "Stock quantity cannot be negative" }
        return this.copy(stockQuantity = newStockQuantity)
    }
    
    /**
     * Create a copy with updated price
     */
    fun withPrice(newPrice: Double): Product {
        require(newPrice > 0) { "Price must be positive" }
        return this.copy(price = newPrice)
    }
    
    /**
     * Create a copy with updated active status
     */
    fun withActiveStatus(isActive: Boolean): Product {
        return this.copy(isActive = isActive)
    }
}