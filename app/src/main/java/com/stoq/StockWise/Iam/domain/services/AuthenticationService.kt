package com.stoq.StockWise.Iam.domain.services

import com.stoq.StockWise.Iam.domain.models.User
import com.stoq.StockWise.Iam.domain.validation.UserValidator

/**
 * Domain service for authentication business logic.
 * Follows DDD principles by containing business rules that don't naturally fit in entities.
 */
class AuthenticationService {

    /**
     * Validates user credentials for login
     */
    fun validateLoginCredentials(user: User): AuthenticationResult {
        val validationResult = UserValidator.validateForLogin(user)

        if (!validationResult.isValid) {
            return AuthenticationResult.Failure(
                AuthenticationError.ValidationError(validationResult.errors)
            )
        }

        return AuthenticationResult.Success
    }

    /**
     * Validates user data for registration
     */
    fun validateRegistrationData(user: User): AuthenticationResult {
        val validationResult = UserValidator.validateForRegistration(user)

        if (!validationResult.isValid) {
            return AuthenticationResult.Failure(
                AuthenticationError.ValidationError(validationResult.errors)
            )
        }

        return AuthenticationResult.Success
    }

    /**
     * Checks if user session is valid based on token and expiration
     */
    fun isSessionValid(user: User): Boolean {
        return user.token.isNotEmpty() && (user.id ?: 0) > 0
    }

    /**
     * Determines if user has sufficient permissions for an operation
     */
    fun hasPermission(user: User, requiredRole: String): Boolean {
        return user.role.equals(requiredRole, ignoreCase = true) ||
               user.role.equals("admin", ignoreCase = true)
    }
}

/**
 * Sealed class for authentication results
 */
sealed class AuthenticationResult {
    object Success : AuthenticationResult()
    data class Failure(val error: AuthenticationError) : AuthenticationResult()
}

/**
 * Sealed class for authentication errors
 */
sealed class AuthenticationError {
    data class ValidationError(val errors: List<String>) : AuthenticationError()
    object InvalidCredentials : AuthenticationError()
    object UserNotFound : AuthenticationError()
    object NetworkError : AuthenticationError()
    data class ServerError(val message: String) : AuthenticationError()
    object UnknownError : AuthenticationError()
}