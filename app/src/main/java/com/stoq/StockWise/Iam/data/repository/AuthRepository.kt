package com.stoq.StockWise.Iam.data.repository

import com.stoq.StockWise.Iam.data.model.LoginRequest
import com.stoq.StockWise.Iam.data.model.RegisterRequest
import com.stoq.StockWise.Iam.data.remote.AuthService
import com.stoq.StockWise.Iam.domain.models.User
import com.stoq.StockWise.shared.data.local.JwtStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface AuthRepository {
    suspend fun login(user: User): Boolean
    suspend fun register(user: User): Boolean
    suspend fun logout(): Boolean
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}
class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {

    override suspend fun login(user: User): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = LoginRequest.fromUser(user)
            val result = authService.login(request)

            result.onSuccess { loginResponse ->
                JwtStorage.saveToken(loginResponse.token)
                return@withContext true
            }.onFailure { exception ->
                println("Login error: ${exception.message}")
                return@withContext false
            }
            return@withContext false
        } catch (e: Exception) {
            println("Repository login error: ${e.message}")
            return@withContext false
        }
    }

    override suspend fun register(user: User): Boolean = withContext(Dispatchers.IO) {
        val request = RegisterRequest.fromUser(user)
        val result = authService.register(request)

        result.onSuccess { loginResponse ->
            JwtStorage.saveToken(loginResponse.token)
            return@withContext true
        }.onFailure {
            return@withContext false
        }
        return@withContext false
    }

    override suspend fun logout(): Boolean = withContext(Dispatchers.IO) {
        JwtStorage.clearToken()
        return@withContext true
    }

    override fun saveToken(token: String) {
        JwtStorage.saveToken(token)
    }

    override fun getToken(): String? {
        return JwtStorage.getToken()
    }

    override fun clearToken() {
        JwtStorage.clearToken()
    }
}