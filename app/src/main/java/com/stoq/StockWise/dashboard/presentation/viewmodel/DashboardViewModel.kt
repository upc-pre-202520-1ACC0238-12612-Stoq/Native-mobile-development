package com.stoq.StockWise.dashboard.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.dashboard.domain.usecase.GetDashboardStatsUseCase
import com.stoq.StockWise.dashboard.domain.usecase.GetRecentProductsUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getStats: GetDashboardStatsUseCase,
    private val getRecent: GetRecentProductsUseCase
) : ViewModel() {

    // -----------------------------------------------------------------------------------------
    // 🔥 ESTE ESTADO SÍ ES OBSERVABLE POR COMPOSE (ESTO ES LO QUE TE FALTABA)
    // -----------------------------------------------------------------------------------------
    private val _uiState = mutableStateOf(DashboardUiState())
    val uiState: DashboardUiState get() = _uiState.value
    // -----------------------------------------------------------------------------------------

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        println("DashboardViewModel: CARGANDO DASHBOARD...")

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                getStats().onSuccess { stats ->
                    getRecent().onSuccess { recentProducts ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            stats = stats,
                            recentProducts = recentProducts,
                            error = null
                        )

                        println("DashboardViewModel => OK: ${_uiState.value}")
                    }.onFailure { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = error.message ?: "Error al obtener productos recientes"
                        )
                        println("Dashboard ERROR (productos recientes) => ${error.message}")
                    }
                }.onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error al obtener estadísticas"
                    )
                    println("Dashboard ERROR (estadísticas) => ${error.message}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
                println("Dashboard ERROR => ${e.message}")
            }
        }
    }
}