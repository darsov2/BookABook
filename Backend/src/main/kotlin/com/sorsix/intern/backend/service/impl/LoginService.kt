package com.sorsix.intern.backend.service.impl

import com.sorsix.intern.backend.api.dtos.RegisterRequest
import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.domain.dto.LoginRequest
import com.sorsix.intern.backend.domain.dto.LoginResponse
import com.sorsix.intern.backend.domain.exceptions.UserNotFoundException
import com.sorsix.intern.backend.repository.UserRepository
import com.sorsix.intern.backend.security.TokenProvider
import io.micrometer.common.util.StringUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service



@Service
class LoginService(private val userRepository: UserRepository,
                   private val passwordEncoder: PasswordEncoder,
                   private val tokenProvider: TokenProvider? = null,
                   private val authenticationManager: AuthenticationManager? = null
) {

    fun login(loginRequest: LoginRequest): LoginResponse {

        System.out.printf("Login request: %s", loginRequest)
        val authentication: Authentication

        val user: User = loginRequest.email?.let {
            userRepository
                ?.findByEmail(it)
        }
            ?: throw UserNotFoundException("Email not registered by administrator yet.")

        if (StringUtils.isEmpty(user.password)) {
            user.password = passwordEncoder!!.encode(loginRequest.password)
            userRepository?.saveAndFlush(user)
        }

        try {
            authentication = authenticationManager!!.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.email,
                    loginRequest.password
                )
            )
        } catch (ex: AuthenticationException) {
            throw BadCredentialsException("Invalid email or password.")
        }

        SecurityContextHolder.getContext().authentication = authentication

        val token: String? = tokenProvider?.createToken(authentication)
        return LoginResponse(token)
    }

    fun register(registerRequest: RegisterRequest) {
        System.out.printf("Login request: %s", registerRequest)


        val existing = userRepository.findByEmail(registerRequest.email)
        if (existing != null) {
            throw RuntimeException("User with email ${registerRequest.email} already exists.")
        }

        userRepository.save(
            User(
                name = registerRequest.firstName + " " + registerRequest.lastName,
                email = registerRequest.email,
                password = passwordEncoder.encode(registerRequest.password),
            )
        )
    }
}