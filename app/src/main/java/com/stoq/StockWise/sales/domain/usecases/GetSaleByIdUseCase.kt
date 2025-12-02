package com.stoq.StockWise.sales.domain.usecases

import com.stoq.StockWise.sales.domain.entities.SaleResponse
import com.stoq.StockWise.sales.domain.repositories.SalesRepository

/**
 * Caso de uso para obtener una venta por su ID.
 */
class GetSaleByIdUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(id: String): Result<SaleResponse> =
        repository.getSaleById(id)
}
