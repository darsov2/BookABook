package com.sorsix.intern.backend.security.oauth2

import com.sorsix.intern.backend.domain.User
import com.sorsix.intern.backend.domain.exceptions.OAuth2AuthenticationProcessingException
import com.sorsix.intern.backend.repository.UserRepository
import com.sorsix.intern.backend.security.UserPrincipal
import com.sorsix.intern.backend.security.oauth2.user.OAuth2UserInfoFactory
import io.micrometer.common.util.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import javax.naming.AuthenticationException

@Service
class CustomOAuth2UserService(private val userRepository: UserRepository) : DefaultOAuth2UserService() {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = DefaultOAuth2UserService().loadUser(userRequest)

        return try {
            processOAuth2User(userRequest, oAuth2User)
        } catch (ex: AuthenticationException) {
            throw ex
        } catch (ex: Exception) {
            throw InternalAuthenticationServiceException(ex.message, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
            oAuth2UserRequest.clientRegistration.registrationId,
            oAuth2User.attributes
        )

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            log.error("Email not found from OAuth2 provider")
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider")
        }

        val userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail())

        val user: User = userOptional?.let { it } ?: run {
            log.error("Email not registered by administrator yet.")
            throw OAuth2AuthenticationProcessingException("Email not registered by administrator yet.")
        }

        return UserPrincipal.create(user, oAuth2User.attributes)
    }
}