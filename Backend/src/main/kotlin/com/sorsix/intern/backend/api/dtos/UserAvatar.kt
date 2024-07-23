package com.sorsix.intern.backend.api.dtos

data class UserAvatar(
    var name: String,
    var lastName: String,
    var imgUrl: String,
    var id: Long,
    var email: String
) {
}