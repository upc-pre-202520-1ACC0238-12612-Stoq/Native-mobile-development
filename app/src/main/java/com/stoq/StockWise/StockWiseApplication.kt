package com.stoq.StockWise

import android.app.Application
import com.stoq.StockWise.Iam.data.di.dataModule
import com.stoq.StockWise.Iam.presentation.di.presentationModule
import com.stoq.StockWise.shared.infrastructure.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Clase Application principal para inicializar Koin y configuraciones globales
 */
class StockWiseApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Inicializar Koin
        startKoin {
            androidContext(this@StockWiseApplication)
            modules(
                networkModule,
                dataModule,
                presentationModule
            )
        }
    }
}
