package com.sorsix.intern.backend.security.oauth2.user

abstract class OAuth2UserInfo(attributes: Map<String, Any>) {
    protected val attributes: Map<String, Any> = attributes.toMap()

    abstract fun getId(): String;
    abstract fun getEmail(): String;
    abstract fun getName(): String;
    abstract fun getImageUrl(): String;
}