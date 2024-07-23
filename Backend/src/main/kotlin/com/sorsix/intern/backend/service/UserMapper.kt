package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.domain.dto.UserResponse

object UserMapper {
    fun mapToUserResponse(user: User): UserResponse {
        val userResponse: UserResponse = UserResponse()
        userResponse.id = user.id
        userResponse.email = user.email
        userResponse.firstname = user.name
        userResponse.lastname = ""
        return userResponse
    }
}
