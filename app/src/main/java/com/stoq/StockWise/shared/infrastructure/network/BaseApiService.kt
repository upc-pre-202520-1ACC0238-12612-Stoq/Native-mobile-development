package com.stoq.StockWise.shared.infrastructure.network

import io.ktor.client.HttpClient
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
     * Crea un HttpClient configurado con serialización JSON y logging.
     * @return HttpClient configurado
     */
    fun createHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}
