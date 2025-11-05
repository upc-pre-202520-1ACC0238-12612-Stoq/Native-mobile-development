package com.stoq.StockWise.inventory.domain.repositories

import com.stoq.StockWise.inventory.domain.entities.Product

/**
 * Contrato de repositorio para la entidad Product.
 * Define las operaciones de acceso a datos para productos dentro de inventarios.
 */
interface ProductRepository {
    
    /**
     * Obtiene todos los productos de un inventario
     * @param inventoryId ID del inventario
     * @return Result con la lista de productos
     */
    suspend fun getProductsByInventoryId(inventoryId: Int): Result<List<Product>>
    
    /**
     * Obtiene un producto por su ID
     * @param productId ID del producto
     * @return Result con el producto o error
     */
    suspend fun getProductById(productId: Int): Result<Product>
    
    /**
     * Crea un nuevo producto
     * @param product Datos del producto a crear
     * @return Result con el producto creado
     */
    suspend fun createProduct(product: Product): Result<Product>
    
    /**
     * Actualiza un producto existente
     * @param product Datos del producto a actualizar
     * @return Result con el producto actualizado
     */
    suspend fun updateProduct(product: Product): Result<Product>
    
    /**
     * Elimina un producto
     * @param productId ID del producto a eliminar
     * @return Result indicando si la operación fue exitosa
     */
    suspend fun deleteProduct(productId: Int): Result<Unit>
    
    /**
     * Busca productos por nombre o descripción
     * @param inventoryId ID del inventario
     * @param query Término de búsqueda
     * @return Result con la lista de productos encontrados
     */
    suspend fun searchProducts(inventoryId: Int, query: String): Result<List<Product>>
    
    /**
     * Obtiene productos con stock bajo
     * @param inventoryId ID del inventario
     * @return Result con la lista de productos con stock bajo
     */
    suspend fun getLowStockProducts(inventoryId: Int): Result<List<Product>>
    
    /**
     * Obtiene productos sin stock
     * @param inventoryId ID del inventario
     * @return Result con la lista de productos sin stock
     */
    suspend fun getOutOfStockProducts(inventoryId: Int): Result<List<Product>>
    
    /**
     * Verifica si un inventario tiene productos
     * @param inventoryId ID del inventario
     * @return Result con true si tiene productos, false si no
     */
    suspend fun hasProducts(inventoryId: Int): Result<Boolean>
    
    /**
     * Obtiene el conteo total de productos en un inventario
     * @param inventoryId ID del inventario
     * @return Result con el número de productos
     */
    suspend fun getProductCount(inventoryId: Int): Result<Int>
}

