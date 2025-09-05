package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginResponse (
    @SerialName("name")
    val name: String? = null,
    @SerialName("token")
    val token: TokenType? = null,
    @SerialName("isCompletedSignUp")
    val isCompletedSignUp: Boolean,
    @SerialName("kakaoId")
    val kakaoId: String,
    @SerialName("apiKey")
    val apiKey: String? = null,
) {
    @Serializable
    data class TokenType(
        @SerialName("accessToken")
        val accessToken: TokenInfoDto,
        @SerialName("refreshToken")
        val refreshToken: TokenInfoDto,
    )
}