package com.stoq.StockWise.product.infrastructure.repositories

import com.stoq.StockWise.product.domain.entities.Product
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import com.stoq.StockWise.product.infrastructure.api.ProductApiService
import com.stoq.StockWise.product.infrastructure.mappers.ProductMapper

/**
 * Implementación del repositorio de productos que consume la API REST
 */
class ProductRepositoryImpl(
    private val apiService: ProductApiService
) : ProductRepository {
    
    override suspend fun getAllProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getAllProducts()
            if (response.isSuccessful) {
                val productDtos = response.body() ?: emptyList()
                val products = ProductMapper.fromDtoList(productDtos)
                Result.success(products)
            } else {
                Result.failure(Exception("Error al obtener productos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val response = apiService.getProductById(id)
            if (response.isSuccessful) {
                val productDto = response.body()
                if (productDto != null) {
                    val product = ProductMapper.fromDto(productDto)
                    Result.success(product)
                } else {
                    Result.failure(Exception("Producto no encontrado"))
                }
            } else {
                Result.failure(Exception("Error al obtener producto: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun searchProductsByName(name: String): Result<List<Product>> {
        return try {
            val response = apiService.getAllProducts()
            if (response.isSuccessful) {
                val productDtos = response.body() ?: emptyList()
                val filteredProducts = productDtos
                    .filter { it.name?.contains(name, ignoreCase = true) == true }
                    .let { ProductMapper.fromDtoList(it) }
                Result.success(filteredProducts)
            } else {
                Result.failure(Exception("Error al buscar productos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}