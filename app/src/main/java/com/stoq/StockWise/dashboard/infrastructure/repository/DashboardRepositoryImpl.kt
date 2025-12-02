package com.stoq.StockWise.dashboard.infrastructure.repository

import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct
import com.stoq.StockWise.dashboard.domain.entities.DashboardStats
import com.stoq.StockWise.dashboard.domain.repository.DashboardRepository
import com.stoq.StockWise.dashboard.infrastructure.api.DashboardApiService
import com.stoq.StockWise.dashboard.infrastructure.dto.InventoryResponseDto
import com.stoq.StockWise.dashboard.infrastructure.dto.ProductDto
import com.stoq.StockWise.dashboard.infrastructure.mapper.DashboardMapper
import java.time.LocalDate

/**
 * Implementación del repositorio del dashboard que consume la API REST
 */
class DashboardRepositoryImpl(
    private val apiService: DashboardApiService
) : DashboardRepository {

    override suspend fun getDashboardStats(): Result<DashboardStats> {
        return try {
            println("DashboardRepositoryImpl: LLAMANDO /api/v1/products ...")
            val productsResponse = apiService.getProducts()

            println("DashboardRepositoryImpl: LLAMANDO /api/v1/inventory ...")
            val inventoryResponse = apiService.getInventory()

            // Verificar respuestas
            if (!productsResponse.isSuccessful) {
                val errorMsg = "Error al obtener productos: Products(${productsResponse.code()}: ${productsResponse.message()})"
                return Result.failure(Exception(errorMsg))
            }

            if (!inventoryResponse.isSuccessful) {
                val errorMsg = "Error al obtener inventario: Inventory(${inventoryResponse.code()}: ${inventoryResponse.message()})"
                return Result.failure(Exception(errorMsg))
            }

            // Procesar datos
            val productsDto = productsResponse.body() ?: emptyList()
            val inventoryDto = inventoryResponse.body()
            val inventoryProductos = inventoryDto?.productos ?: emptyList()

            // Fecha de hoy en formato ISO (yyyy-MM-dd)
            val today = LocalDate.now().toString()

            // totalProducts: cantidad de elementos de /products
            val totalProducts = productsDto.size

            // movementsToday: contar productos de inventory.productos que tienen fechaEntrada del día actual
            val movementsToday = inventoryProductos.count { producto ->
                producto.fechaEntrada?.startsWith(today) == true
            }

            val stats = DashboardStats(
                totalProducts = totalProducts,
                movementsToday = movementsToday
            )

            println("DashboardRepositoryImpl: Total productos = $totalProducts")
            println("DashboardRepositoryImpl: Movimientos hoy = $movementsToday")

            Result.success(stats)
        } catch (e: Exception) {
            println("DashboardRepositoryImpl: Error = ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getRecentProducts(): Result<List<DashboardProduct>> {
        return try {
            val response = apiService.getInventory()
            if (response.isSuccessful) {
                val inventoryDto = response.body()
                val productos = inventoryDto?.productos ?: emptyList()

                // recentProducts: productos de inventory ordenados por fechaEntrada desc, tomar 5 más recientes
                val recentProducts = DashboardMapper.fromInventoryProductDtoList(productos)
                    .take(5)

                Result.success(recentProducts)
            } else {
                Result.failure(Exception("Error al obtener productos recientes: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}