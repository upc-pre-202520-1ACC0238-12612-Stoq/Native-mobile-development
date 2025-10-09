package com.stoq.StockWise.ProductCatalog.presentation.di

import com.stoq.StockWise.ProductCatalog.presentation.viewmodels.ProductCatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Dependency injection module for Product Catalog presentation layer.
 * Provides ViewModels and other presentation components.
 */
val ProductCatalogPresentationModule = module {

    /**
     * Product Catalog ViewModel
     */
    viewModel {
        ProductCatalogViewModel()
    }
}