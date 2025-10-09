package com.stoq.StockWise

import android.app.Application
import com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductCatalogViewModel
import com.stoq.StockWise.shared.data.local.JwtStorage
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

class StoqApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JwtStorage.initialize(this)

        // Limpiar token para forzar login
        JwtStorage.clearToken()

        // Initialize Koin
        startKoin {
            androidContext(this@StoqApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel { ProductCatalogViewModel() }
}