package com.stoq.StockWise.shared.infrastructure.repositories

import android.content.Context
import android.content.SharedPreferences
import com.stoq.StockWise.shared.domain.entities.JwtToken
import com.stoq.StockWise.shared.domain.repositories.JwtRepository
import com.stoq.StockWise.shared.infrastructure.mappers.JwtTokenMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementación del repositorio JWT usando SharedPreferences.
 * Esta implementación pertenece al Shared Kernel ya que es utilizada por múltiples bounded contexts.
 */
class JwtRepositoryImpl(
    private val context: Context
) : JwtRepository {
    
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    companion object {
        private const val PREFS_NAME = "stockwise_prefs"
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_EXPIRES_AT = "jwt_expires_at"
        private const val KEY_ISSUED_AT = "jwt_issued_at"
    }
    
    override suspend fun saveToken(token: JwtToken): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            sharedPreferences.edit()
                .putString(KEY_TOKEN, token.value)
                .putLong(KEY_EXPIRES_AT, token.expiresAt ?: 0L)
                .putLong(KEY_ISSUED_AT, token.issuedAt ?: 0L)
                .apply()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getToken(): Result<JwtToken?> = withContext(Dispatchers.IO) {
        try {
            val tokenValue = sharedPreferences.getString(KEY_TOKEN, null)
            if (tokenValue == null) {
                return@withContext Result.success(null)
            }
            
            val expiresAt = sharedPreferences.getLong(KEY_EXPIRES_AT, 0L)
            val issuedAt = sharedPreferences.getLong(KEY_ISSUED_AT, 0L)
            
            val token = JwtToken(
                value = tokenValue,
                expiresAt = if (expiresAt > 0) expiresAt else null,
                issuedAt = if (issuedAt > 0) issuedAt else null
            )
            
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun clearToken(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            sharedPreferences.edit()
                .remove(KEY_TOKEN)
                .remove(KEY_EXPIRES_AT)
                .remove(KEY_ISSUED_AT)
                .apply()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun hasValidToken(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val tokenResult = getToken()
            tokenResult.fold(
                onSuccess = { token -> 
                    Result.success(token?.isValid() ?: false)
                },
                onFailure = { 
                    Result.failure(it)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
