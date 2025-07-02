package com.example.sparka_v0.data.retrofit.response

data class RegisterResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)