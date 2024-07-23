package com.sorsix.intern.backend.service

import com.sorsix.intern.backend.api.dtos.UserAvatar
import com.sorsix.intern.backend.domain.Customer
import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.domain.dto.UserResponse

interface UserService {
    fun getUserInfoById(id: Long): UserResponse
    fun findById(id: Long): Customer?
    fun findCustomerByIdAvatar(id: Long): UserAvatar
}