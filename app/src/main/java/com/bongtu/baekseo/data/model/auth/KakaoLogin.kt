package com.bongtu.baekseo.data.model.auth

data class KakaoLogin(
    val name: String,
    val accessToken: String,
    val refreshToken: String,
    val isCompletedSignUp: Boolean,
    val kakaoId: String,
    val apiKey: String,
)