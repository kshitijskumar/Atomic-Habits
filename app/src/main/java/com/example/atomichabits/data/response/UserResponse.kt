package com.example.atomichabits.data.response

data class UserResponse(
    val name: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val streak: Int = 0
)
