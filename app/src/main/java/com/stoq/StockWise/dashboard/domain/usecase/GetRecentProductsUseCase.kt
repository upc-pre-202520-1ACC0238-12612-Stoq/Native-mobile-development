package com.stoq.StockWise.dashboard.domain.usecase

import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct
import com.stoq.StockWise.dashboard.domain.repository.DashboardRepository

/**
 * Caso de uso para obtener productos recientes del dashboard.
 */
class GetRecentProductsUseCase(private val repo: DashboardRepository) {
    suspend operator fun invoke(): Result<List<DashboardProduct>> = repo.getRecentProducts()
}