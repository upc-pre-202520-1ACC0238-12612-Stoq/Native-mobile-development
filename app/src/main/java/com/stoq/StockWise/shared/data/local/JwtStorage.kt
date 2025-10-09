package com.stoq.StockWise.shared.data.local

import android.content.Context
import android.content.SharedPreferences

object JwtStorage {
    private const val PREFS_NAME = "stockwise_prefs"
    private const val KEY_TOKEN = "jwt_token"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply()
    }
}