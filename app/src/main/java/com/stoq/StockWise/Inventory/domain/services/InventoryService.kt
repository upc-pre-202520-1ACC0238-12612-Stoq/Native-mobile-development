package com.stoq.StockWise.Inventory.domain.services

import com.stoq.StockWise.Inventory.domain.models.*
import com.stoq.StockWise.Inventory.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Domain service for Inventory business logic.
 * Contains business rules that don't naturally fit in entities or repositories.
 */
class InventoryService(
    private val inventoryRepository: InventoryRepository
) {
    
    /**
     * Process a stock movement
     */
    suspend fun processStockMovement(movement: StockMovement): Result<StockMovement> {
        return try {
            // Business rule: Validate movement
            val validationResult = validateMovement(movement)
            if (!validationResult.isValid) {
                return Result.failure(Exception(validationResult.error))
            }

            // Process the movement
            inventoryRepository.saveStockMovement(movement)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get current stock for a product
     */
    suspend fun getCurrentStock(productId: String): Result<Int> {
        return try {
            val stockItem = inventoryRepository.getCurrentStock(productId)
            stockItem?.let {
                Result.success(it.quantity)
            } ?: Result.failure(Exception("Stock item not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get all stock movements for a stock item
     */
    suspend fun getStockMovements(stockItemId: String): List<StockMovement> {
        return inventoryRepository.getStockMovements(stockItemId)
    }

    /**
     * Get low stock alerts
     */
    suspend fun getLowStockAlerts(): List<LowStockAlert> {
        return inventoryRepository.getLowStockAlerts()
    }
    
    /**
     * Validate stock movement against business rules
     */
    private suspend fun validateMovement(movement: StockMovement): ValidationResult {
        // Business rule: Movement quantity cannot be zero
        if (movement.quantity == 0) {
            return ValidationResult(false, "Movement quantity cannot be zero")
        }
        
        // Business rule: Reason is required for adjustments
        if (movement.type == MovementType.ADJUSTMENT && movement.reason.isBlank()) {
            return ValidationResult(false, "Reason is required for adjustments")
        }
        
        // Business rule: Cannot have negative stock
        if (movement.type == MovementType.OUT) {
            val currentStock = inventoryRepository.getCurrentStock(movement.stockItemId)
            if (currentStock != null && currentStock.quantity < movement.quantity) {
                return ValidationResult(false, "Insufficient stock for this movement")
            }
        }
        
        return ValidationResult(true)
    }
    
    /**
     * Data class for validation results
     */
    data class ValidationResult(
        val isValid: Boolean,
        val error: String = ""
    )
    
  }