package com.stoq.StockWise.product.presentation.di

import com.stoq.StockWise.product.presentation.viewmodels.ProductViewModel
import com.stoq.StockWise.product.presentation.viewmodels.ScanProductViewModel
import com.stoq.StockWise.product.infrastructure.repositories.ProductRepositoryImpl
import com.stoq.StockWise.product.infrastructure.vision.ProductVisionService
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import com.stoq.StockWise.product.domain.usecases.ScanProductUseCase
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productPresentationModule = module {

    // 🔹 Dependencias existentes
    single { NetworkClient.createProductApiService(get<JwtRepository>()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    viewModel { ProductViewModel(get()) }

    // 🔹 Nuevas dependencias para Plan D (visión ML / cámara)
    single { ProductVisionService() }
    single { ScanProductUseCase(get()) }
    viewModel { ScanProductViewModel(get()) }
}
