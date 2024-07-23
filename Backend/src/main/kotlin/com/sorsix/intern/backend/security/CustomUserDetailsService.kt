package com.sorsix.intern.backend.security

import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository?.findByEmail(email) ?: throw UsernameNotFoundException(String.format("User not found with email: %s.", email))

        return UserPrincipal.create(user)
    }

    fun loadUserById(id: Long): UserDetails {
        val user: User = userRepository?.findByIdKt(id) ?: throw UsernameNotFoundException(String.format("User not found with ID: %s.", id))

        return UserPrincipal.create(user)
    }
}