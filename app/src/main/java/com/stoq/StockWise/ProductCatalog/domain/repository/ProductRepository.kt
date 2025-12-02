package com.stoq.StockWise.ProductCatalog.domain.repository

import com.stoq.StockWise.ProductCatalog.domain.models.Product
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for Product domain.
 * Follows DDD repository pattern to abstract data access.
 */
interface ProductRepository {
    
    /**
     * Get all products
     */
    suspend fun getAllProducts(): Flow<List<Product>>
    
    /**
     * Get product by ID
     */
    suspend fun getProductById(id: String): Result<Product>
    
    /**
     * Get products by category
     */
    suspend fun getProductsByCategory(categoryId: String): Flow<List<Product>>
    
    /**
     * Search products by name or SKU
     */
    suspend fun searchProducts(query: String): Flow<List<Product>>
    
    /**
     * Get active products only
     */
    suspend fun getActiveProducts(): Flow<List<Product>>
    
    /**
     * Get products that need restocking
     */
    suspend fun getProductsNeedingRestock(): Flow<List<Product>>
    
    /**
     * Save a product (create or update)
     */
    suspend fun saveProduct(product: Product): Result<Product>
    
    /**
     * Delete a product
     */
    suspend fun deleteProduct(id: String): Result<Unit>
    
    /**
     * Update product stock
     */
    suspend fun updateProductStock(productId: String, quantity: Int): Result<Product>
}