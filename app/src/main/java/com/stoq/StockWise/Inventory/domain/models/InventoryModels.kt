package com.stoq.StockWise.Inventory.domain.models

import com.stoq.StockWise.ProductCatalog.domain.models.Product

data class StockItem(
    val id: String,
    val product: Product,
    val quantity: Int,
    val location: String,
    val lastUpdated: Long,
    val minimumThreshold: Int = 0
) {
    init {
        require(quantity >= 0) { "Quantity cannot be negative" }
        require(minimumThreshold >= 0) { "Minimum threshold cannot be negative" }
        require(location.isNotBlank()) { "Location cannot be blank" }
    }

    val isLowStock: Boolean
        get() = quantity <= minimumThreshold

    fun needsReorder(maxCapacity: Int): Boolean {
        return quantity < (maxCapacity * 0.2).toInt() // Reorder when less than 20% capacity
    }
}

data class StockMovement(
    val id: String,
    val stockItemId: String,
    val type: MovementType,
    val quantity: Int,
    val reason: String,
    val timestamp: Long,
    val userId: String
) {
    init {
        require(quantity > 0) { "Movement quantity must be positive" }
        require(reason.isNotBlank()) { "Reason cannot be blank" }
        require(userId.isNotBlank()) { "User ID cannot be blank" }
    }
}

enum class MovementType {
    IN,
    OUT,
    ADJUSTMENT,
    TRANSFER,
    DAMAGED,
    RETURNED
}

data class LowStockAlert(
    val stockItem: StockItem,
    val currentQuantity: Int,
    val threshold: Int,
    val suggestedReorderQuantity: Int
) {
    val severity: AlertSeverity
        get() = when {
            currentQuantity == 0 -> AlertSeverity.CRITICAL
            currentQuantity < threshold / 2 -> AlertSeverity.HIGH
            else -> AlertSeverity.MEDIUM
        }
}

enum class AlertSeverity {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}