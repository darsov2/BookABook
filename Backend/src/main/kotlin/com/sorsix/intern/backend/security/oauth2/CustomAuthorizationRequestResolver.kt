package com.sorsix.intern.backend.security.oauth2

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.security.crypto.keygen.StringKeyGenerator
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class CustomAuthorizationRequestResolver(repo: ClientRegistrationRepository?, authorizationRequestBaseUri: String?) :
    OAuth2AuthorizationRequestResolver {
    private val defaultResolver: OAuth2AuthorizationRequestResolver =
        DefaultOAuth2AuthorizationRequestResolver(repo, authorizationRequestBaseUri)
    private val secureKeyGenerator: StringKeyGenerator =
        Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96)

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        val req = defaultResolver.resolve(request)
        return customizeAuthorizationRequest(req)
    }

    override fun resolve(request: HttpServletRequest, clientRegistrationId: String): OAuth2AuthorizationRequest? {
        return null
    }


    private fun customizeAuthorizationRequest(req: OAuth2AuthorizationRequest): OAuth2AuthorizationRequest? {
        if (Objects.isNull(req)) {
            return null
        }

        val attributes: MutableMap<String, Any> = HashMap(req.attributes)
        val additionalParameters: MutableMap<String, Any> = HashMap(req.additionalParameters)
        addPkceParameters(attributes, additionalParameters)
        return OAuth2AuthorizationRequest.from(req)
            .attributes(attributes)
            .additionalParameters(additionalParameters)
            .build()
    }

    private fun addPkceParameters(attributes: MutableMap<String, Any>, additionalParameters: MutableMap<String, Any>) {
        val codeVerifier = secureKeyGenerator.generateKey()
        attributes[PkceParameterNames.CODE_VERIFIER] = codeVerifier
        try {
            val codeChallenge = createHash(codeVerifier)
            additionalParameters[PkceParameterNames.CODE_CHALLENGE] = codeChallenge
            additionalParameters[PkceParameterNames.CODE_CHALLENGE_METHOD] = "S256"
        } catch (e: NoSuchAlgorithmException) {
            additionalParameters[PkceParameterNames.CODE_CHALLENGE] = codeVerifier
        }
    }

    companion object {
        @Throws(NoSuchAlgorithmException::class)
        private fun createHash(value: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(value.toByteArray(StandardCharsets.US_ASCII))
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
        }
    }
}