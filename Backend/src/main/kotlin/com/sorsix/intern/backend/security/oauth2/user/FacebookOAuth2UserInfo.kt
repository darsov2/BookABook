package com.sorsix.intern.backend.security.oauth2.user

class FacebookOAuth2UserInfo(attributes: Map<String, Any>) : OAuth2UserInfo(attributes) {
    override fun getId(): String = this.attributes["id"] as String
    override fun getEmail(): String = this.attributes["email"] as String
    override fun getName(): String = this.attributes["name"] as String
    override fun getImageUrl(): String {
        val pictureObj = this.attributes["picture"] as? Map<String, Any>
        val dataObj = pictureObj?.get("data") as? Map<String, Any>
        return dataObj?.get("url") as? String ?: ""
    }

}