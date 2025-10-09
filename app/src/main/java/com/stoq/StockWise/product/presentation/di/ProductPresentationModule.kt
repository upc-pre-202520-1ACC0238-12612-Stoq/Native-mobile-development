package com.stoq.StockWise.product.presentation.di

import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.product.infrastructure.repositories.ProductRepositoryImpl
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productPresentationModule = module {
    // Product dependencies
    single { NetworkClient.createProductApiService() }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    viewModel { ProductViewModel(get()) }
}
