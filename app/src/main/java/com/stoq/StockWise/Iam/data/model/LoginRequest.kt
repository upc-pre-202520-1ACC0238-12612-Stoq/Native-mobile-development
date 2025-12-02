package com.stoq.StockWise.Iam.data.model

import com.stoq.StockWise.Iam.domain.models.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String = "",
    val password: String = ""
){
    companion object {
        fun fromUser(user: User): LoginRequest{
            return LoginRequest(
                email = user.email,
                password = user.password
            )
        }
    }
}