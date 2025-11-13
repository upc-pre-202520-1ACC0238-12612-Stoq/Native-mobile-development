package com.stoq.StockWise.inventory.presentation.di

import com.stoq.StockWise.inventory.domain.repositories.InventoryRepository
import com.stoq.StockWise.inventory.infrastructure.api.InventoryApiService
import com.stoq.StockWise.inventory.infrastructure.repositories.InventoryRepositoryImpl
import com.stoq.StockWise.inventory.presentation.viewmodels.InventorySetupViewModel
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val inventoryPresentationModule = module {
    // Inventory setup dependencies
    single<InventoryApiService> {
        NetworkClient.createInventoryApiService<InventoryApiService>(get())
    }
    single<InventoryRepository> {
        InventoryRepositoryImpl(get())
    }
    viewModel {
        InventorySetupViewModel(get())
    }
}
