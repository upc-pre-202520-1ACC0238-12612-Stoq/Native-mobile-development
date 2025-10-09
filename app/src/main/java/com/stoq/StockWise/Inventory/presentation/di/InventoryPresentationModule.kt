package com.stoq.StockWise.inventory.presentation.di

import com.stoq.StockWise.inventory.presentation.viewmodels.InventorySetupViewModel
import com.stoq.StockWise.inventory.domain.services.InventoryDomainService
import com.stoq.StockWise.inventory.infrastructure.repositories.InventoryRepositoryImpl
import com.stoq.StockWise.inventory.domain.repositories.InventoryRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val inventoryPresentationModule = module {
    // Inventory setup dependencies
    single<InventoryRepository> { InventoryRepositoryImpl() }
    single { InventoryDomainService(get()) }
    viewModel { InventorySetupViewModel(get()) }
}
