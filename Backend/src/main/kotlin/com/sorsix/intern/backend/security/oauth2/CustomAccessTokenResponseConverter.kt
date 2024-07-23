package com.sorsix.intern.backend.security.oauth2

import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream


class CustomAccessTokenResponseConverter :
    Converter<Map<String?, Any?>?, OAuth2AccessTokenResponse?> {
    override fun convert(tokenResponseParameters: Map<String?, Any?>): OAuth2AccessTokenResponse {
        val accessToken = tokenResponseParameters[OAuth2ParameterNames.ACCESS_TOKEN] as String?

        val accessTokenType = OAuth2AccessToken.TokenType.BEARER

        var expiresIn: Long = 0
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.EXPIRES_IN)) {
            try {
                expiresIn = (tokenResponseParameters[OAuth2ParameterNames.EXPIRES_IN] as Int?)!!.toLong()
            } catch (ex: NumberFormatException) {
                error("Number format exception during access token response conversion, " + ex.message)
            }
        }

        var scopes: Set<String>? = emptySet()
        if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            val scope = tokenResponseParameters[OAuth2ParameterNames.SCOPE] as String?
            scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).collect(Collectors.toSet())
        }

        val additionalParameters: MutableMap<String?, Any?> = LinkedHashMap()
        tokenResponseParameters.entries.stream()
            .filter { e: Map.Entry<String?, Any?> ->
                !TOKEN_RESPONSE_PARAMETER_NAMES.contains(
                    e.key
                )
            }
            .forEach { e: Map.Entry<String?, Any?> ->
                additionalParameters[e.key] = e.value
            }

        return OAuth2AccessTokenResponse.withToken(accessToken)
            .tokenType(accessTokenType)
            .expiresIn(expiresIn)
            .scopes(scopes)
            .additionalParameters(additionalParameters)
            .build()
    }

    companion object {
        private val TOKEN_RESPONSE_PARAMETER_NAMES: Set<String?> = Stream.of(
            OAuth2ParameterNames.ACCESS_TOKEN,
            OAuth2ParameterNames.TOKEN_TYPE,
            OAuth2ParameterNames.EXPIRES_IN,
            OAuth2ParameterNames.REFRESH_TOKEN,
            OAuth2ParameterNames.SCOPE
        ).collect(Collectors.toSet())
    }
}