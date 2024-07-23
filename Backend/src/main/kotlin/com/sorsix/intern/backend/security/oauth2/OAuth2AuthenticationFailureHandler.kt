package com.sorsix.intern.backend.security.oauth2

import com.sorsix.intern.backend.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import com.sorsix.intern.backend.service.CookieUtils
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException

@Component
class OAuth2AuthenticationFailureHandler : SimpleUrlAuthenticationFailureHandler() {
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository? = null

    @Throws(IOException::class)
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        var targetUrl: String = CookieUtils
            .getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            ?.let { obj: Cookie -> obj.value }
            ?: "/"

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception.localizedMessage)
            .build().toUriString()

        httpCookieOAuth2AuthorizationRequestRepository!!.removeAuthorizationRequestCookies(request, response)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}