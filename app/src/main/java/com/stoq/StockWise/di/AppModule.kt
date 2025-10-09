package com.stoq.StockWise.di

import com.stoq.StockWise.Iam.data.di.DataModule as IamDataModule
import com.stoq.StockWise.Iam.presentation.di.PresentationModule as IamPresentationModule
import com.stoq.StockWise.shared.data.local.JwtStorage
import android.content.Context

/**
 * Central dependency injection module for the application.
 * This module provides access to all bounded contexts' modules.
 */
object AppModule {
    
    /**
     * Initialize the application DI system.
     * Must be called before using any dependency injection.
     */
    fun initialize(context: Context) {
        JwtStorage.initialize(context)
    }
    
    /**
     * Get the IAM data module.
     */
    fun getIamDataModule() = IamDataModule
    
    /**
     * Get the IAM presentation module.
     */
    fun getIamPresentationModule() = IamPresentationModule
    
    /**
     * Clean up resources when needed.
     */
    fun cleanup() {
        // Clean up resources if needed
    }
}