package com.stoq.StockWise.dashboard.domain.usecase

import com.stoq.StockWise.dashboard.domain.entities.DashboardStats
import com.stoq.StockWise.dashboard.domain.repository.DashboardRepository

/**
 * Caso de uso para obtener las estadísticas del dashboard.
 */
class GetDashboardStatsUseCase(private val repo: DashboardRepository) {
    suspend operator fun invoke(): Result<DashboardStats> = repo.getDashboardStats()
}