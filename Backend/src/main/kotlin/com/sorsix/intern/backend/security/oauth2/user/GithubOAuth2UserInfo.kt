package com.sorsix.intern.backend.security.oauth2.user

class GithubOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {
    override fun getId(): String = this.attributes["sub"] as String
    override fun getEmail(): String = this.attributes["email"] as String
    override fun getName(): String = this.attributes["name"] as String
    override fun getImageUrl(): String = this.attributes["avatar_url"] as String
}