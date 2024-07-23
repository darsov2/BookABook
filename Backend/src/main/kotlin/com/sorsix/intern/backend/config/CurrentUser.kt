package com.sorsix.intern.backend.config

import org.springframework.security.core.annotation.AuthenticationPrincipal


@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@AuthenticationPrincipal
annotation class CurrentUser
