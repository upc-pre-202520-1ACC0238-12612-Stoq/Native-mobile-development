package com.stoq.StockWise.shared.infrastructure.network

import android.content.Context
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.shared.infrastructure.repositories.JwtRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single<JwtRepository> { JwtRepositoryImpl(androidContext()) }
    single<HttpClient> { BaseApiService.createHttpClient(get<JwtRepository>()) }
}

/**
 * Módulo de red que proporciona instancias compartidas de servicios de red.
 * Este módulo pertenece al Shared Kernel ya que es utilizado por múltiples bounded contexts.
 */
object NetworkModule {
    
    private var context: Context? = null
    
    /**
     * Inicializa el módulo de red con el contexto de la aplicación.
     * @param context El contexto de la aplicación
     */
    fun initialize(context: Context) {
        this.context = context
    }
    
    /**
     * Obtiene el HttpClient configurado con autenticación JWT.
     * @return HttpClient configurado
     */
    fun getHttpClient(): HttpClient {
        val jwtRepository = getJwtRepository()
        return BaseApiService.createHttpClient(jwtRepository)
    }
    
    /**
     * Obtiene el repositorio JWT.
     * @return JwtRepository configurado
     */
    fun getJwtRepository(): JwtRepository {
        val ctx = context ?: throw IllegalStateException("NetworkModule no ha sido inicializado. Llama a initialize() primero.")
        return JwtRepositoryImpl(ctx)
    }
}
