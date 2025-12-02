package com.stoq.StockWise.Iam.data.model

import com.stoq.StockWise.Iam.domain.models.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val username: String,
    val password: String,
    val email: String
){
    companion object {
        fun fromUser(user: User): RegisterRequest {
            return RegisterRequest(
                username = user.username,
                password = user.password,
                email = user.email
            )
        }
    }
}