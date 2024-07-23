package com.sorsix.intern.backend.api.dtos

data class RegisterRequest(
    var email: String,
    var password: String,
    var firstName: String,
    var lastName: String,
)
