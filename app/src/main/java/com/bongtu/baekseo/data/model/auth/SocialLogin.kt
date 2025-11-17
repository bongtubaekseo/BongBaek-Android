package com.bongtu.baekseo.data.model.auth

data class SocialLogin(
    val name: String,
    val accessToken: String,
    val refreshToken: String,
    val isCompletedSignUp: Boolean,
    val oauthId: String,
    val apiKey: String,
)
