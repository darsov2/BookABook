package com.sorsix.intern.backend.security

import com.sorsix.intern.backend.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User


data class UserPrincipal(
    val id: Long,
    private val email: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority>
) : OAuth2User, UserDetails {
    private var attributes: Map<String?, Any?>? = null

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getAttributes(): Map<String, Any> {
        return emptyMap()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getName(): String {
        return id.toString()
    }

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities = listOf<GrantedAuthority>() // can be implemented later if needed

            return UserPrincipal(
                user.id,
                user.email,
                user.password,
                authorities
            )
        }

        fun create(user: User, attributes: Map<String?, Any?>?): UserPrincipal {
            val userPrincipal = create(user)
            userPrincipal.attributes = attributes
            return userPrincipal
        }
    }
}
