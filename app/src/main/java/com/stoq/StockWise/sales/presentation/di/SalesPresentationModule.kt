package com.stoq.StockWise.sales.presentation.di

import com.stoq.StockWise.sales.infrastructure.api.SalesApiService
import com.stoq.StockWise.sales.infrastructure.repositories.SalesRepositoryImpl
import com.stoq.StockWise.sales.domain.repositories.SalesRepository
import com.stoq.StockWise.sales.domain.usecases.CreateSaleUseCase
import com.stoq.StockWise.sales.domain.usecases.GetSaleByIdUseCase
import com.stoq.StockWise.sales.domain.usecases.CheckStockUseCase
import com.stoq.StockWise.sales.presentation.viewmodel.SalesViewModel
import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val salesPresentationModule = module {

    // API
    single<SalesApiService> { NetworkClient.createSalesApiService(get()) }

    // Repositorio
    single<SalesRepository> { SalesRepositoryImpl(get(), get()) }

    // Use Cases
    factory { CreateSaleUseCase(get()) }
    factory { GetSaleByIdUseCase(get()) }
    factory { CheckStockUseCase(get()) }

    // ViewModel
    viewModel {
        SalesViewModel(
            createSaleUseCase = get(),
            checkStockUseCase = get(),
            productRepository = get()
        )
    }
}
