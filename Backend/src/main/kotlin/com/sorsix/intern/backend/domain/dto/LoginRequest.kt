package com.sorsix.intern.backend.domain.dto

data class LoginRequest(
    val email: String? = null,
    val password: String? = null
    ) {
}