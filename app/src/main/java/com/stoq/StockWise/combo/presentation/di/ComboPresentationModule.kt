package com.stoq.StockWise.combo.presentation.di

import com.stoq.StockWise.combo.infrastructure.api.ComboApiService
import com.stoq.StockWise.combo.infrastructure.repository.ComboRepositoryImpl
import com.stoq.StockWise.combo.domain.repository.ComboRepository
import com.stoq.StockWise.combo.domain.usecase.GetCombosUseCase
import com.stoq.StockWise.combo.domain.usecase.CreateComboUseCase
import com.stoq.StockWise.combo.presentation.viewmodel.CombosViewModel
import com.stoq.StockWise.shared.infrastructure.network.NetworkClient
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.product.domain.repositories.ProductRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val comboPresentationModule = module {

    // API
    single<ComboApiService> {
        NetworkClient.createComboApiService(get<JwtRepository>())
    }

    // Repositorio
    single<ComboRepository> { ComboRepositoryImpl(get()) }

    // Use Cases
    factory { GetCombosUseCase(get()) }
    factory { CreateComboUseCase(get()) }

    // ViewModel
    viewModel {
        CombosViewModel(
            getCombos = get(),          // 🔥 nombre correcto
            createCombo = get(),        // 🔥 nombre correcto
            productRepository = get()   // 🔥 nombre correcto
        )
    }
}
