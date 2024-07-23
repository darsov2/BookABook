package com.sorsix.intern.backend.domain.exceptions

class UserNotFoundException(override val message: String?) : RuntimeException(message) {
}