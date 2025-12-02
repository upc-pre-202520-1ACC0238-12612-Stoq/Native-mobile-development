package com.stoq.StockWise.shared.infrastructure.network

import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.http.HttpHeaders

/**
 * Plugin de Ktor para agregar automáticamente el token JWT a todas las peticiones HTTP.
 * Este plugin pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 * 
 * El plugin intercepta todas las peticiones HTTP y agrega el header Authorization
 * con el token JWT almacenado en el repositorio.
 */
class AuthTokenPlugin private constructor() {
    var jwtRepository: JwtRepository? = null
    
    companion object : HttpClientPlugin<AuthTokenPlugin.Config, AuthTokenPlugin> {
        override val key = io.ktor.util.AttributeKey<AuthTokenPlugin>("AuthToken")
        
        override fun prepare(block: Config.() -> Unit): AuthTokenPlugin {
            val config = Config().apply(block)
            return AuthTokenPlugin().apply {
                jwtRepository = config.jwtRepository
            }
        }
        
        override fun install(plugin: AuthTokenPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(io.ktor.client.request.HttpRequestPipeline.State) {
                val jwtRepository = plugin.jwtRepository
                if (jwtRepository != null) {
                    val token = jwtRepository.getToken().getOrNull()?.value
                    
                    if (token != null && token.isNotBlank()) {
                        context.headers.append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
            }
        }
    }
    
    class Config {
        var jwtRepository: JwtRepository? = null
    }
}

