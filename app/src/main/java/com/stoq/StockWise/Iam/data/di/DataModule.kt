package com.stoq.StockWise.Iam.data.di

import android.content.Context
import com.stoq.StockWise.Iam.data.remote.AuthService
import com.stoq.StockWise.Iam.data.remote.AuthServiceImpl
import com.stoq.StockWise.Iam.data.repository.AuthRepository
import com.stoq.StockWise.Iam.data.repository.AuthRepositoryImpl
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.shared.infrastructure.network.NetworkModule
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {
    single<AuthService> { 
        AuthServiceImpl(get())
    }
    
    single<AuthRepository> { 
        AuthRepositoryImpl(get(), get())
    }
}

object DataModule {
    private var context: Context? = null
    
    fun initialize(context: Context) {
        this.context = context
        NetworkModule.initialize(context)
    }
    
    private val httpClient: HttpClient by lazy {
        NetworkModule.getHttpClient()
    }

    private val authService: AuthService by lazy {
        AuthServiceImpl(httpClient)
    }
    
    private val jwtRepository: JwtRepository by lazy {
        NetworkModule.getJwtRepository()
    }

    fun getAuthRepository(): AuthRepository {
        return AuthRepositoryImpl(authService, jwtRepository)
    }
}