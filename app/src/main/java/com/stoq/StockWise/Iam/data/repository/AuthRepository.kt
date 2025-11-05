package com.stoq.StockWise.Iam.data.repository

import com.stoq.StockWise.Iam.data.model.LoginRequest
import com.stoq.StockWise.Iam.data.model.RegisterRequest
import com.stoq.StockWise.Iam.data.remote.AuthService
import com.stoq.StockWise.Iam.domain.models.User
import com.stoq.StockWise.shared.domain.entities.JwtToken
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthRepository {
    suspend fun login(user: User): Result<Boolean>
    suspend fun register(user: User): Result<Boolean>
    suspend fun logout(): Result<Boolean>
    suspend fun saveToken(token: String): Result<Unit>
    suspend fun getToken(): Result<String?>
    suspend fun clearToken(): Result<Unit>
}
class AuthRepositoryImpl(
    private val authService: AuthService,
    private val jwtRepository: JwtRepository
) : AuthRepository {

    override suspend fun login(user: User): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = LoginRequest.fromUser(user)
            val result = authService.login(request)

            result.fold(
                onSuccess = { loginResponse ->
                    val jwtToken = JwtToken(value = loginResponse.token)
                    jwtRepository.saveToken(jwtToken).fold(
                        onSuccess = { Result.success(true) },
                        onFailure = { Result.failure(it) }
                    )
                },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(user: User): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val request = RegisterRequest.fromUser(user)
            val result = authService.register(request)

            result.fold(
                onSuccess = { loginResponse ->
                    val jwtToken = JwtToken(value = loginResponse.token)
                    jwtRepository.saveToken(jwtToken).fold(
                        onSuccess = { Result.success(true) },
                        onFailure = { Result.failure(it) }
                    )
                },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            jwtRepository.clearToken().fold(
                onSuccess = { Result.success(true) },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveToken(token: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val jwtToken = JwtToken(value = token)
            jwtRepository.saveToken(jwtToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getToken(): Result<String?> = withContext(Dispatchers.IO) {
        try {
            jwtRepository.getToken().fold(
                onSuccess = { jwtToken -> Result.success(jwtToken?.value) },
                onFailure = { Result.failure(it) }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearToken(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            jwtRepository.clearToken()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}