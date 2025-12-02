package com.stoq.StockWise.sales.domain.usecases

import com.stoq.StockWise.sales.domain.entities.SaleRequest
import com.stoq.StockWise.sales.domain.entities.SaleResponse
import com.stoq.StockWise.sales.domain.repositories.SalesRepository

/**
 * Caso de uso para crear una nueva venta.
 */
class CreateSaleUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(request: SaleRequest): Result<SaleResponse> =
        repository.createSale(request)
}
