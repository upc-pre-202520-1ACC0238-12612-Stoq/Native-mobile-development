package com.stoq.StockWise.Iam.data.remote

import com.stoq.StockWise.Iam.data.model.LoginRequest
import com.stoq.StockWise.Iam.data.model.LoginResponse
import com.stoq.StockWise.Iam.data.model.RegisterRequest
import com.stoq.StockWise.sharedkernel.infrastructure.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface AuthService {
    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun register(request: RegisterRequest): Result<LoginResponse>
}

class AuthServiceImpl(private val client: HttpClient) : AuthService {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = client.post(ApiConfig.IdentityAccess.LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(request: RegisterRequest): Result<LoginResponse> {
        return try {
            val response = client.post(ApiConfig.IdentityAccess.REGISTER) {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}