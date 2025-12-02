package com.stoq.StockWise.inventory.domain.repository

import com.stoq.StockWise.inventory.domain.models.*

interface InventoryRepository {
    suspend fun getCurrentStock(productId: String): StockItem?
    suspend fun updateStock(stockItemId: String, newQuantity: Int): Result<StockItem>
    suspend fun getStockMovements(stockItemId: String): List<StockMovement>
    suspend fun saveStockMovement(movement: StockMovement): Result<StockMovement>
    suspend fun getLowStockAlerts(): List<LowStockAlert>
    suspend fun getAllStockItems(): List<StockItem>
    suspend fun addStockItem(stockItem: StockItem): Result<StockItem>
    suspend fun deleteStockItem(stockItemId: String): Result<Unit>
}