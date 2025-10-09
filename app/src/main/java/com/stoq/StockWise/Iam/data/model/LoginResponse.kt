package com.stoq.StockWise.Iam.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val id: Int,
    val name: String,
    val lastName: String,
    val token: String,
    val role: String
)