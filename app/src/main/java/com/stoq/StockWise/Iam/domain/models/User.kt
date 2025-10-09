package com.stoq.StockWise.Iam.domain.models

data class User (
    val id: Int? = null,
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val lastName: String = "",
    val token: String = "",
    val role: String = ""
)