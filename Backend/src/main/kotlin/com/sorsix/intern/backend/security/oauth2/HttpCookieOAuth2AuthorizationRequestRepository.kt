package com.sorsix.intern.backend.security.oauth2

import com.sorsix.intern.backend.service.CookieUtils
import io.micrometer.common.util.StringUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component


@Component
class HttpCookieOAuth2AuthorizationRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest?> {
    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return CookieUtils
            .getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            ?.let { CookieUtils.deserialize(it, OAuth2AuthorizationRequest::class.java) }
            ?: null
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            return
        }

        CookieUtils.addCookie(
            response,
            OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
            CookieUtils.serialize(authorizationRequest),
            cookieExpireSeconds
        )
        val redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds)
        }
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): OAuth2AuthorizationRequest? {
        return this.loadAuthorizationRequest(request)
    }

    fun removeAuthorizationRequestCookies(request: HttpServletRequest, response: HttpServletResponse) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }

    companion object {
        const val OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME: String = "oauth2_auth_request"
        const val REDIRECT_URI_PARAM_COOKIE_NAME: String = "redirect_uri"
        private const val cookieExpireSeconds = 180
    }
}