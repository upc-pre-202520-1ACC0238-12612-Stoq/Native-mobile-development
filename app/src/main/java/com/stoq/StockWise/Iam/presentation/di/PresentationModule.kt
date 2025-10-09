package com.stoq.StockWise.Iam.presentation.di

import com.stoq.StockWise.presentation.viewmodels.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.stoq.StockWise.Iam.data.di.DataModule

object PresentationModule {
    fun getAuthViewModel(): AuthViewModel {
        return AuthViewModel(DataModule.getAuthRepository())
    }
}