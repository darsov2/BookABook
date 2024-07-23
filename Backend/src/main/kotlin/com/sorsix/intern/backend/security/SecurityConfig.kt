package com.sorsix.intern.backend.security

import com.sorsix.intern.backend.security.oauth2.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.client.RestTemplate
import java.beans.Customizer
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val tokenAuthenticationFilter: TokenAuthenticationFilter,
    private val clientRegistrationRepository: ClientRegistrationRepository
) {

    private val OAUTH2_BASE_URI = "/oauth2/authorize"
    private val OAUTH2_REDIRECTION_ENDPOINT = "/oauth2/callback/*"

    @Bean
    fun cookieOAuth2AuthorizationRequestRepository(): HttpCookieOAuth2AuthorizationRequestRepository {
        return HttpCookieOAuth2AuthorizationRequestRepository()
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
//            .cors { Customizer.withDefaults() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { it.disable() }.logout { }
            .httpBasic { it.disable() }

        http.authorizeRequests { auth ->
            auth.requestMatchers("/token/refresh/**").permitAll()
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/auth/**", "/oauth2/**").permitAll()
                .anyRequest().permitAll()
        }

        http.oauth2Login { oauth2 ->
            oauth2.authorizationEndpoint { authorizationEndpointConfig ->
                authorizationEndpointConfig.baseUri(OAUTH2_BASE_URI)
                    .authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
//                    .authorizationRequestResolver(
//                        CustomAuthorizationRequestResolver(
//                            clientRegistrationRepository,
//                            OAUTH2_BASE_URI
//                        )
//                    )
            }
                .redirectionEndpoint { redirectionEndpointConfig ->
                    redirectionEndpointConfig.baseUri(OAUTH2_REDIRECTION_ENDPOINT)
                }
                .userInfoEndpoint { userInfoEndpointConfig ->
                    userInfoEndpointConfig.userService(customOAuth2UserService)
                }
                .tokenEndpoint { tokenEndpointConfig ->
                    tokenEndpointConfig.accessTokenResponseClient(authorizationCodeTokenResponseClient())
                }
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
        }
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    private fun authorizationCodeTokenResponseClient(): OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
        val tokenResponseHttpMessageConverter = OAuth2AccessTokenResponseHttpMessageConverter()
        tokenResponseHttpMessageConverter.setAccessTokenResponseConverter(CustomAccessTokenResponseConverter())

        val restTemplate = RestTemplate(Arrays.asList(FormHttpMessageConverter(), tokenResponseHttpMessageConverter))
        restTemplate.errorHandler = OAuth2ErrorResponseErrorHandler()

        val tokenResponseClient = DefaultAuthorizationCodeTokenResponseClient()
        tokenResponseClient.setRestOperations(restTemplate)

        return tokenResponseClient
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}