package com.stoq.StockWise.Iam.presentation.di

import com.stoq.StockWise.Iam.presentation.viewmodels.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val iamPresentationModule = module {
    viewModel { AuthViewModel(get()) }
}