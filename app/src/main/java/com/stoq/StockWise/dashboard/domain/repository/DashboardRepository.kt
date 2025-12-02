package com.stoq.StockWise.dashboard.domain.repository

import com.stoq.StockWise.dashboard.domain.entities.DashboardStats
import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct

/**
 * Interfaz del repositorio del dashboard siguiendo Domain-Driven Design
 */
interface DashboardRepository {

    /**
     * Obtiene las estadísticas del dashboard.
     */
    suspend fun getDashboardStats(): Result<DashboardStats>

    /**
     * Obtiene los productos recientes (por ejemplo, últimos 5).
     */
    suspend fun getRecentProducts(): Result<List<DashboardProduct>>
}
