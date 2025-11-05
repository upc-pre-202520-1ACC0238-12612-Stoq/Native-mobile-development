package com.stoq.StockWise.shared.infrastructure.network

import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Servicio base para configurar HttpClient con configuración común.
 * Este servicio pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 */
object BaseApiService {
    
    /**
     * Crea un HttpClient configurado con serialización JSON, logging y autenticación JWT.
     * @param jwtRepository Repositorio JWT para obtener el token de autenticación
     * @return HttpClient configurado con el plugin de autenticación
     */
    fun createHttpClient(jwtRepository: JwtRepository): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = ApiConfig.CONNECT_TIMEOUT_SECONDS * 1000
                connectTimeoutMillis = ApiConfig.CONNECT_TIMEOUT_SECONDS * 1000
                socketTimeoutMillis = ApiConfig.READ_TIMEOUT_SECONDS * 1000
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.ALL
            }
            install(AuthTokenPlugin) {
                this.jwtRepository = jwtRepository
            }
        }
    }
}
