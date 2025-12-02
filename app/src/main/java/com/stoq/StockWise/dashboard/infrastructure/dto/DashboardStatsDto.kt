package com.stoq.StockWise.dashboard.infrastructure.dto

/**
 * DTO para producto reciente del dashboard
 */
data class DashboardRecentProductDto(
    val id: Int,
    val name: String,
    val date: String,
    val stock: Int,
    val price: Double?,
    val description: String?
)

/**
 * DTO para stats del dashboard
 */
data class DashboardStatsDto(
    val totalProducts: Int,
    val movementsToday: Int,
    val recentProducts: List<DashboardRecentProductDto>
)
