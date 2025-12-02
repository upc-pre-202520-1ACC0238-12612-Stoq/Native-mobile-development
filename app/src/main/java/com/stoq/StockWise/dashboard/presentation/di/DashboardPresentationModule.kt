package com.stoq.StockWise.dashboard.presentation.di

import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.dashboard.infrastructure.api.DashboardApiService
import com.stoq.StockWise.dashboard.infrastructure.repository.DashboardRepositoryImpl
import com.stoq.StockWise.dashboard.domain.repository.DashboardRepository
import com.stoq.StockWise.dashboard.domain.usecase.GetDashboardStatsUseCase
import com.stoq.StockWise.dashboard.domain.usecase.GetRecentProductsUseCase
import com.stoq.StockWise.dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardPresentationModule = module {

    // API del Dashboard
    single<DashboardApiService> {
        NetworkClient.createDashboardApiService(get<JwtRepository>())
    }

    // repositorio
    single<DashboardRepository> { DashboardRepositoryImpl(get()) }

    // casos de uso
    factory { GetDashboardStatsUseCase(get()) }
    factory { GetRecentProductsUseCase(get()) }

    // viewmodel
    viewModel {
        DashboardViewModel(
            getStats = get(),
            getRecent = get()
        )
    }
}