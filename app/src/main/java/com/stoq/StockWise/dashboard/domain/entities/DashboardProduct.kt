package com.stoq.StockWise.dashboard.domain.entities

/**
 * Entidad de dominio que representa un producto en el dashboard.
 */
data class DashboardProduct(
    val id: Int,
    val name: String,
    val date: String,
    val stock: Int,
    val price: Double?,
    val description: String?
)
