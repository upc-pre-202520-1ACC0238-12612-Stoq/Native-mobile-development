package com.stoq.StockWise.Iam.data.di

import com.stoq.StockWise.Iam.data.remote.AuthService
import com.stoq.StockWise.Iam.data.remote.AuthServiceImpl
import com.stoq.StockWise.Iam.data.repository.AuthRepository
import com.stoq.StockWise.Iam.data.repository.AuthRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<AuthService> { 
        AuthServiceImpl(get(), androidContext())
    }
    
    single<AuthRepository> { 
        AuthRepositoryImpl(get(), get())
    }
}