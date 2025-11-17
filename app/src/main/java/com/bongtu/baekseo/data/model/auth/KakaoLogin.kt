package com.bongtu.baekseo.data.model.auth

data class KakaoLogin(
    val name: String,
    val accessToken: String,
    val refreshToken: String,
    val isCompletedSignUp: Boolean,
    val oauthId: String,
    val oauthProvider: String,
    val apiKey: String,
)