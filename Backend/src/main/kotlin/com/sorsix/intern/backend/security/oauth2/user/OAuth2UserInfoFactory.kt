package com.sorsix.intern.backend.security.oauth2.user

import com.sorsix.intern.backend.domain.Enum.AuthProvider
import com.sorsix.intern.backend.domain.exceptions.OAuth2AuthenticationProcessingException
import java.util.*

class OAuth2UserInfoFactory {
    companion object {
        @JvmStatic
        fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any>): OAuth2UserInfo {
            return when (registrationId.lowercase(Locale.getDefault())) {
                AuthProvider.google.toString() -> GoogleOAuth2UserInfo(attributes)
                AuthProvider.facebook.toString() -> FacebookOAuth2UserInfo(attributes)
                AuthProvider.github.toString() -> GithubOAuth2UserInfo(attributes)
                else -> throw OAuth2AuthenticationProcessingException("Login with $registrationId is not supported.")
            }
        }
    }
}