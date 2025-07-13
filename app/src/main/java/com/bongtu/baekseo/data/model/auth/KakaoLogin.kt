package com.bongtu.baekseo.data.model.auth

data class KakaoLogin(
    val accessToken: String,
    val refreshToken: String,
    val isCompletedSignUp: Boolean,
    val kakaoId: Long,
)