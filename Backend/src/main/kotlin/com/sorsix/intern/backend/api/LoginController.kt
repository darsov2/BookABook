package com.sorsix.intern.backend.api

import com.sorsix.intern.backend.api.dtos.RegisterRequest
import com.sorsix.intern.backend.domain.dto.LoginRequest
import com.sorsix.intern.backend.domain.dto.LoginResponse
import com.sorsix.intern.backend.repository.UserRepository
import com.sorsix.intern.backend.service.impl.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@CrossOrigin
class LoginController(private val loginService: LoginService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest?): LoginResponse? {
        val loginResponse = loginRequest?.let { loginService.login(it) }
        return loginResponse
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<*> {
        loginService.register(registerRequest)
        return ResponseEntity.ok().build<Any>()
    }
}