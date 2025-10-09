package com.stoq.StockWise.Iam.domain.models

data class User (
    val username: String = "",
    val password: String = "",
    val email: String = "",
)