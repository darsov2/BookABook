package com.sorsix.intern.backend.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.sorsix.intern.backend.config.AppProperties
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Autowired

@Service
class TokenProvider {
    @Autowired
    private val appProperties: AppProperties? = null
    private var ALGORITHM: Algorithm? = null

    fun createToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal

        ALGORITHM = Algorithm.HMAC256(appProperties?.auth?.tokenSecret)

        val now = Date()
        val expirationDate: Date = Date(now.time + appProperties?.auth?.tokenExpirationMilis!!)

        return JWT.create()
            .withSubject(userPrincipal.id.toString())
            .withIssuedAt(now)
            .withExpiresAt(expirationDate)
            .sign(ALGORITHM)
    }

    fun getUserIdFromToken(token: String?): Long {
        val verifier: JWTVerifier = JWT.require(ALGORITHM).build()
        val decodedJWT: DecodedJWT = verifier.verify(token)
        val subject: String = decodedJWT.getSubject()
        return subject.toLong()
    }

    fun validateToken(token: String?): Boolean {
        try {
            val verifier: JWTVerifier = JWT.require(ALGORITHM).build()
            verifier.verify(token)
            return true
        } catch (e: Exception) {
//            log.error("Invalid or expired JWT.")
        }
        return false
    }
}
