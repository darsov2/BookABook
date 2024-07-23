package com.sorsix.intern.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service

@Service
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    val auth: Auth = Auth(),
    val oAuth2: OAuth2 = OAuth2(),
) {
    data class Auth(
        var tokenSecret: String = "",
        var tokenExpirationMilis: Long = 0
    ) {}

    data class OAuth2(
        var authorizedRedirectUris: List<String> = listOf("http://localhost:4200/oauth2/redirect")
    ) {
      fun authorizedRedirectUris(authorizedRedirectUris: List<String>): OAuth2 {
          this.authorizedRedirectUris = authorizedRedirectUris
          return this
      }
    }
}