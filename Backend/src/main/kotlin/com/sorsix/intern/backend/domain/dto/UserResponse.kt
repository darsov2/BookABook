package com.sorsix.intern.backend.domain.dto

import com.sorsix.intern.backend.domain.Enum.AuthProvider

data class UserResponse(
    var id: Long? = null,
    var email: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var authProvider: AuthProvider? = null,
    var name: String? = null,
    var imageUrl: String? = null,
    var libraryId: Long? = null,
    var customerId: Long? = null
) {}
