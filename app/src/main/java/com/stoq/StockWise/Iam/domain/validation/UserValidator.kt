package com.stoq.StockWise.Iam.domain.validation

import android.util.Patterns
import com.stoq.StockWise.Iam.domain.models.User

/**
 * Domain validator for User entities following DDD principles.
 * Contains business rules for user validation.
 */
object UserValidator {

    private const val MIN_PASSWORD_LENGTH = 4
    private const val MIN_USERNAME_LENGTH = 2

    data class ValidationResult(
        val isValid: Boolean,
        val errors: List<String> = emptyList()
    )

    fun validateForLogin(user: User): ValidationResult {
        val errors = mutableListOf<String>()

        if (user.email.isBlank()) {
            errors.add("Email is required")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errors.add("Invalid email format")
        }

        if (user.password.isBlank()) {
            errors.add("Password is required")
        } else if (user.password.length < MIN_PASSWORD_LENGTH) {
            errors.add("Password must be at least $MIN_PASSWORD_LENGTH characters")
        }

        return ValidationResult(errors.isEmpty(), errors)
    }

    fun validateForRegistration(user: User): ValidationResult {
        val errors = mutableListOf<String>()

        // Email validation
        if (user.email.isBlank()) {
            errors.add("Email is required")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errors.add("Invalid email format")
        }

        // Password validation
        if (user.password.isBlank()) {
            errors.add("Password is required")
        } else if (user.password.length < MIN_PASSWORD_LENGTH) {
            errors.add("Password must be at least $MIN_PASSWORD_LENGTH characters")
        }

        // Username validation
        if (user.username.isBlank()) {
            errors.add("Username is required")
        } else if (user.username.length < MIN_USERNAME_LENGTH) {
            errors.add("Username must be at least $MIN_USERNAME_LENGTH characters")
        }

        return ValidationResult(errors.isEmpty(), errors)
    }
}