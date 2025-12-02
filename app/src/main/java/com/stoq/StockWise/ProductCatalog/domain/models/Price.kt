package com.stoq.StockWise.ProductCatalog.domain.models

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Value object for Price following DDD principles.
 * Represents monetary values with proper validation and formatting.
 */
@Serializable
data class Price(
    private val value: Double
) {
    init {
        require(value >= 0) { "Price cannot be negative" }
        require(value <= Double.MAX_VALUE) { "Price exceeds maximum allowed value" }
    }

    val amount: Double
        get() = value

    val formatted: String
        get() = "$${BigDecimal(value).setScale(2, RoundingMode.HALF_UP)}"

    fun isZero(): Boolean = value == 0.0

    fun isPositive(): Boolean = value > 0

    fun multiply(quantity: Int): Price {
        require(quantity >= 0) { "Quantity cannot be negative" }
        return Price(value * quantity)
    }

    fun add(other: Price): Price {
        return Price(this.value + other.value)
    }

    fun subtract(other: Price): Price {
        val result = this.value - other.value
        require(result >= 0) { "Resulting price cannot be negative" }
        return Price(result)
    }

    fun applyDiscount(percentage: Double): Price {
        require(percentage in 0.0..100.0) { "Discount percentage must be between 0 and 100" }
        val discountAmount = value * (percentage / 100)
        return Price(value - discountAmount)
    }

    fun applyTax(percentage: Double): Price {
        require(percentage >= 0) { "Tax percentage cannot be negative" }
        val taxAmount = value * (percentage / 100)
        return Price(value + taxAmount)
    }

    override fun toString(): String = formatted

    companion object {
        val ZERO = Price(0.0)

        fun fromDouble(value: Double): Price {
            return Price(value)
        }

        fun fromString(value: String): Price {
            val parsedValue = value.toDoubleOrNull()
                ?: throw IllegalArgumentException("Invalid price format: $value")
            return Price(parsedValue)
        }
    }
}