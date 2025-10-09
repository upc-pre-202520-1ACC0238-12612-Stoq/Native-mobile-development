package com.stoq.StockWise.Inventory.domain.aggregate

import com.stoq.StockWise.Inventory.domain.models.*
import com.stoq.StockWise.ProductCatalog.domain.models.Product
import java.time.LocalDateTime

/**
 * Inventory aggregate root following DDD principles.
 * Manages inventory operations and ensures consistency within the aggregate boundary.
 */
class InventoryAggregate(
    val id: String,
    val productId: String,
    val productName: String,
    initialQuantity: Int = 0,
    val location: String,
    val minimumThreshold: Int = 0,
    val maximumCapacity: Int = Int.MAX_VALUE
) {
    private var _currentQuantity: Int = initialQuantity
    private val _movements: MutableList<StockMovement> = mutableListOf()
    private var _lastUpdated: LocalDateTime = LocalDateTime.now()

    val currentQuantity: Int get() = _currentQuantity
    val movements: List<StockMovement> get() = _movements.toList()
    val lastUpdated: LocalDateTime get() = _lastUpdated

    init {
        require(initialQuantity >= 0) { "Initial quantity cannot be negative" }
        require(minimumThreshold >= 0) { "Minimum threshold cannot be negative" }
        require(maximumCapacity > 0) { "Maximum capacity must be positive" }
        require(location.isNotBlank()) { "Location cannot be blank" }
        require(initialQuantity <= maximumCapacity) { "Initial quantity cannot exceed maximum capacity" }
    }

    /**
     * Process stock IN movement
     */
    fun processStockIn(
        quantity: Int,
        reason: String,
        userId: String,
        timestamp: LocalDateTime = LocalDateTime.now()
    ): InventoryOperationResult {
        return try {
            require(quantity > 0) { "Stock in quantity must be positive" }
            require(reason.isNotBlank()) { "Reason cannot be blank" }
            require(userId.isNotBlank()) { "User ID cannot be blank" }

            val newTotal = _currentQuantity + quantity
            require(newTotal <= maximumCapacity) { "Stock quantity would exceed maximum capacity" }

            val movement = StockMovement(
                id = generateMovementId(),
                stockItemId = id,
                type = MovementType.IN,
                quantity = quantity,
                reason = reason,
                timestamp = timestamp.toEpochSecond(java.time.ZoneOffset.UTC),
                userId = userId
            )

            _movements.add(movement)
            _currentQuantity = newTotal
            _lastUpdated = timestamp

            InventoryOperationResult.Success(movement)
        } catch (e: Exception) {
            InventoryOperationResult.Failure(InventoryError.ValidationFailed(e.message ?: "Unknown error"))
        }
    }

    /**
     * Process stock OUT movement
     */
    fun processStockOut(
        quantity: Int,
        reason: String,
        userId: String,
        timestamp: LocalDateTime = LocalDateTime.now()
    ): InventoryOperationResult {
        return try {
            require(quantity > 0) { "Stock out quantity must be positive" }
            require(reason.isNotBlank()) { "Reason cannot be blank" }
            require(userId.isNotBlank()) { "User ID cannot be blank" }

            if (_currentQuantity < quantity) {
                return InventoryOperationResult.Failure(
                    InventoryError.InsufficientStock("Available: $_currentQuantity, Requested: $quantity")
                )
            }

            val movement = StockMovement(
                id = generateMovementId(),
                stockItemId = id,
                type = MovementType.OUT,
                quantity = quantity,
                reason = reason,
                timestamp = timestamp.toEpochSecond(java.time.ZoneOffset.UTC),
                userId = userId
            )

            _movements.add(movement)
            _currentQuantity -= quantity
            _lastUpdated = timestamp

            InventoryOperationResult.Success(movement)
        } catch (e: Exception) {
            InventoryOperationResult.Failure(InventoryError.ValidationFailed(e.message ?: "Unknown error"))
        }
    }

    /**
     * Process stock adjustment
     */
    fun processStockAdjustment(
        newQuantity: Int,
        reason: String,
        userId: String,
        timestamp: LocalDateTime = LocalDateTime.now()
    ): InventoryOperationResult {
        return try {
            require(newQuantity >= 0) { "Adjusted quantity cannot be negative" }
            require(reason.isNotBlank()) { "Reason cannot be blank" }
            require(userId.isNotBlank()) { "User ID cannot be blank" }
            require(newQuantity <= maximumCapacity) { "Adjusted quantity cannot exceed maximum capacity" }

            val adjustmentQuantity = kotlin.math.abs(newQuantity - _currentQuantity)
            val movementType = if (newQuantity > _currentQuantity) MovementType.ADJUSTMENT else MovementType.ADJUSTMENT

            val movement = StockMovement(
                id = generateMovementId(),
                stockItemId = id,
                type = MovementType.ADJUSTMENT,
                quantity = adjustmentQuantity,
                reason = reason,
                timestamp = timestamp.toEpochSecond(java.time.ZoneOffset.UTC),
                userId = userId
            )

            _movements.add(movement)
            _currentQuantity = newQuantity
            _lastUpdated = timestamp

            InventoryOperationResult.Success(movement)
        } catch (e: Exception) {
            InventoryOperationResult.Failure(InventoryError.ValidationFailed(e.message ?: "Unknown error"))
        }
    }

    /**
     * Check if stock is low
     */
    fun isLowStock(): Boolean = _currentQuantity <= minimumThreshold

    /**
     * Check if stock is critical (zero)
     */
    fun isCriticalStock(): Boolean = _currentQuantity == 0

    /**
     * Calculate stock availability percentage
     */
    fun getAvailabilityPercentage(): Double {
        return if (maximumCapacity == 0) 0.0 else (_currentQuantity.toDouble() / maximumCapacity) * 100
    }

    /**
     * Get low stock alert if needed
     */
    fun getLowStockAlert(): LowStockAlert? {
        return if (isLowStock()) {
            LowStockAlert(
                stockItem = StockItem(
                    id = id,
                    product = Product(
                        id = productId,
                        name = productName,
                        description = "",
                        price = 0.0,
                        categoryId = "",
                        sku = ""
                    ),
                    quantity = _currentQuantity,
                    location = location,
                    lastUpdated = _lastUpdated.toEpochSecond(java.time.ZoneOffset.UTC),
                    minimumThreshold = minimumThreshold
                ),
                currentQuantity = _currentQuantity,
                threshold = minimumThreshold,
                suggestedReorderQuantity = (minimumThreshold * 1.5).toInt() - _currentQuantity
            )
        } else null
    }

    private fun generateMovementId(): String = "MOV_${System.currentTimeMillis()}_${_movements.size}"

    companion object {
        fun create(
            productId: String,
            productName: String,
            initialQuantity: Int,
            location: String,
            minimumThreshold: Int = 0,
            maximumCapacity: Int = Int.MAX_VALUE
        ): InventoryAggregate {
            return InventoryAggregate(
                id = "INV_${System.currentTimeMillis()}",
                productId = productId,
                productName = productName,
                initialQuantity = initialQuantity,
                location = location,
                minimumThreshold = minimumThreshold,
                maximumCapacity = maximumCapacity
            )
        }
    }
}

/**
 * Sealed class for operation results
 */
sealed class InventoryOperationResult {
    data class Success(val movement: StockMovement) : InventoryOperationResult()
    data class Failure(val error: InventoryError) : InventoryOperationResult()
}

/**
 * Sealed class for inventory errors
 */
sealed class InventoryError {
    data class ValidationFailed(val message: String) : InventoryError()
    data class InsufficientStock(val message: String) : InventoryError()
    data class CapacityExceeded(val message: String) : InventoryError()
    data class MovementNotFound(val message: String) : InventoryError()
    data class UnknownError(val message: String) : InventoryError()
}