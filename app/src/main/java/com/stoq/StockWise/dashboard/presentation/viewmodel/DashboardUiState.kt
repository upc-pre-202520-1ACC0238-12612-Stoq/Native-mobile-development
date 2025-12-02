package com.stoq.StockWise.dashboard.presentation.viewmodel

import com.stoq.StockWise.dashboard.domain.entities.DashboardProduct
import com.stoq.StockWise.dashboard.domain.entities.DashboardStats

data class DashboardUiState(
    val isLoading: Boolean = true,

    // Datos
    val stats: DashboardStats? = null,
    val activeBranches: Int = 4,   // igual que Flutter (puede cambiarse después)
    val recentProducts: List<DashboardProduct> = emptyList(),

    // Error
    val error: String? = null
)