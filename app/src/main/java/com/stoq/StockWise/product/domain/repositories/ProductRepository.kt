package com.stoq.StockWise.product.domain.repositories

import com.stoq.StockWise.product.domain.entities.Product

/**
 * Interfaz del repositorio de productos siguiendo Domain-Driven Design
 */
interface ProductRepository {
    /**
     * Obtiene todos los productos disponibles
     */
    suspend fun getAllProducts(): Result<List<Product>>
    
    /**
     * Obtiene un producto por su ID
     */
    suspend fun getProductById(id: Int): Result<Product>
    
    /**
     * Busca productos por nombre
     */
    suspend fun searchProductsByName(name: String): Result<List<Product>>
}