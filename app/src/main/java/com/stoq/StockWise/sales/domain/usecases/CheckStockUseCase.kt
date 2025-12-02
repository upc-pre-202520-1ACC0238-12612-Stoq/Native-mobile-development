package com.stoq.StockWise.sales.domain.usecases

import com.stoq.StockWise.sales.domain.entities.StockCheck
import com.stoq.StockWise.sales.domain.repositories.SalesRepository

/**
 * Caso de uso para verificar el stock de un producto.
 */
class CheckStockUseCase(private val repository: SalesRepository) {
    suspend operator fun invoke(productId: Int): Result<StockCheck> =
        repository.checkStock(productId)
}
