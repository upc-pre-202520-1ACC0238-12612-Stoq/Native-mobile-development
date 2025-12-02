package com.stoq.StockWise.sales.domain.repositories

import com.stoq.StockWise.sales.domain.entities.SaleRequest
import com.stoq.StockWise.sales.domain.entities.SaleResponse
import com.stoq.StockWise.sales.domain.entities.StockCheck

/**
 * Interfaz del repositorio de ventas siguiendo Domain-Driven Design
 */
interface SalesRepository {

    /**
     * Crea una nueva venta
     */
    suspend fun createSale(request: SaleRequest): Result<SaleResponse>

    /**
     * Obtiene una venta por su ID
     */
    suspend fun getSaleById(id: String): Result<SaleResponse>

    /**
     * Verifica el stock disponible de un producto
     */
    suspend fun checkStock(productId: Int): Result<StockCheck>
}
