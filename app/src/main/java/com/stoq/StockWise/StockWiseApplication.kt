package com.stoq.StockWise

import android.app.Application
import com.stoq.StockWise.Iam.data.di.dataModule
import com.stoq.StockWise.Iam.presentation.di.iamPresentationModule
import com.stoq.StockWise.inventory.presentation.di.inventoryPresentationModule
import com.stoq.StockWise.product.presentation.di.productPresentationModule
import com.stoq.StockWise.shared.infrastructure.network.networkModule
import com.stoq.StockWise.dashboard.presentation.di.dashboardPresentationModule
import com.stoq.StockWise.combo.presentation.di.comboPresentationModule
import com.stoq.StockWise.sales.presentation.di.salesPresentationModule
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
                iamPresentationModule,
                inventoryPresentationModule,
                productPresentationModule,
                dashboardPresentationModule,
                comboPresentationModule,
                salesPresentationModule
            )
        }
    }
}
