package com.stoq.StockWise.di

import com.stoq.StockWise.Iam.data.di.dataModule
import com.stoq.StockWise.Iam.presentation.di.iamPresentationModule
import com.stoq.StockWise.inventory.presentation.di.inventoryPresentationModule
import com.stoq.StockWise.product.presentation.di.productPresentationModule

/**
 * Central dependency injection module for the application.
 * This module provides access to all bounded contexts' modules.
 */
object AppModule {
    
    /**
     * Get the IAM data module.
     */
    fun getIamDataModule() = dataModule
    
    /**
     * Get the IAM presentation module.
     */
    fun getIamPresentationModule() = iamPresentationModule
    
    /**
     * Get the Inventory presentation module.
     */
    fun getInventoryPresentationModule() = inventoryPresentationModule
    
    /**
     * Get the Product presentation module.
     */
    fun getProductPresentationModule() = productPresentationModule
    
    /**
     * Clean up resources when needed.
     */
    fun cleanup() {
        // Clean up resources if needed
    }
}