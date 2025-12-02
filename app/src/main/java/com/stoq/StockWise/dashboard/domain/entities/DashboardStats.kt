package com.stoq.StockWise.dashboard.domain.entities

/**
 * Entidad de dominio que representa las estadísticas del dashboard.
 */
data class DashboardStats(
    val totalProducts: Int,
    val movementsToday: Int
)