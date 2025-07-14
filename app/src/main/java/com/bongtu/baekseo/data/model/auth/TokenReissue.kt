package com.bongtu.baekseo.data.model.auth

data class TokenReissue(
    val accessToken: String,
    val refreshToken: String,
)