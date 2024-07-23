package com.sorsix.intern.backend.security.oauth2

import com.sorsix.intern.backend.config.AppProperties
import com.sorsix.intern.backend.security.TokenProvider
import com.sorsix.intern.backend.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import com.sorsix.intern.backend.service.CookieUtils
import jakarta.servlet.ServletException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import java.net.URI
import java.util.*

@Component
class OAuth2AuthenticationSuccessHandler : SimpleUrlAuthenticationSuccessHandler() {
    @Autowired
    private val tokenProvider: TokenProvider? = null
    @Autowired
    private val appProperties: AppProperties? = null
    @Autowired
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository? = null



    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): String {
        val redirectUri: String? = CookieUtils
            .getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            ?.let { it.value }
            ?: null

        if (redirectUri != null && !isAuthorizedRedirectUri(redirectUri)!!) {
            throw BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
        }

        val targetUrl = redirectUri ?: defaultTargetUrl

        val token: String? = tokenProvider?.createToken(authentication)

        return UriComponentsBuilder.fromUriString("http://localhost:4200/login-callback")
            .queryParam("token", token)
            .build().toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest?, response: HttpServletResponse?) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository!!.removeAuthorizationRequestCookies(request!!, response!!)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean? {
        val clientRedirectUri = URI.create(uri)

        return appProperties?.oAuth2?.authorizedRedirectUris
            ?.stream()
            ?.anyMatch { authorizedRedirectUri: String ->
                val authorizedURI = URI.create(authorizedRedirectUri)
                (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true)
                        && authorizedURI.port == clientRedirectUri.port)
            }
    }
}