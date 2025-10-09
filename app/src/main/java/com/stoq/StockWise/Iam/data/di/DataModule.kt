package com.stoq.StockWise.Iam.data.di

import com.stoq.StockWise.Iam.data.remote.AuthService
import com.stoq.StockWise.Iam.data.remote.AuthServiceImpl
import com.stoq.StockWise.Iam.data.repository.AuthRepository
import com.stoq.StockWise.Iam.data.repository.AuthRepositoryImpl
import com.stoq.StockWise.sharedkernel.infrastructure.network.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object DataModule {
    private val httpClient: HttpClient by lazy {
        HttpClient(CIO) {
            defaultRequest {
                url(ApiConfig.BASE_URL)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                    prettyPrint = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.INFO
            }
        }
    }

    private val authService: AuthService by lazy {
        AuthServiceImpl(httpClient)
    }

    fun getAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(authService)
    }
}