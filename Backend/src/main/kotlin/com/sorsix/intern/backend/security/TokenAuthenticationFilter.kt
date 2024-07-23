package com.sorsix.intern.backend.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Service
class TokenAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private val tokenProvider: TokenProvider? = null
    @Autowired
    private val userDetailsService: CustomUserDetailsService? = null


    private fun getJWTFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length)
        }

        return null
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        try {
            val jwt = getJWTFromRequest(request)

            if (StringUtils.hasText(jwt) && tokenProvider!!.validateToken(jwt)) {
                val userId = tokenProvider.getUserIdFromToken(jwt)
                val userDetails = userDetailsService!!.loadUserById(userId)
                val authenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        } catch (ex: Exception) {
            error("Could not set user authentication in security context." +  ex.message)
        }

        filterChain.doFilter(request, response)
    }
}