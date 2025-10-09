package com.stoq.StockWise.Iam.data.remote

import com.stoq.StockWise.Iam.data.model.LoginRequest
import com.stoq.StockWise.Iam.data.model.LoginResponse
import com.stoq.StockWise.Iam.data.model.RegisterRequest
import com.stoq.StockWise.sharedkernel.infrastructure.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.HttpStatusCode
import java.io.IOException

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
                header("Accept", "application/json")
                header("User-Agent", "StockWise-Android/1.0")
            }
            
            val responseBody = response.body<LoginResponse>()
            
            when (response.status) {
                HttpStatusCode.OK -> {
                    println("Login successful for user: ${request.email}")
                    Result.success(responseBody)
                }
                HttpStatusCode.Unauthorized -> {
                    println("Authentication failed for user: ${request.email}")
                    Result.failure(Exception("Invalid credentials"))
                }
                HttpStatusCode.BadRequest -> {
                    println("Bad request: Invalid request format")
                    Result.failure(Exception("Invalid request"))
                }
                else -> {
                    println("Server error: ${response.status.value} - ${response.status.description}")
                    Result.failure(Exception("Server error: ${response.status.value}"))
                }
            }
        } catch (e: ConnectTimeoutException) {
            println("Connection timeout: ${e.message}")
            Result.failure(Exception("Connection timeout. Please check your internet connection."))
        } catch (e: IOException) {
            println("Network error: ${e.message}")
            Result.failure(Exception("Network error. Please check your internet connection."))
        } catch (e: Exception) {
            println("Unexpected error: ${e.message}")
            Result.failure(Exception("Unexpected error: ${e.message}"))
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