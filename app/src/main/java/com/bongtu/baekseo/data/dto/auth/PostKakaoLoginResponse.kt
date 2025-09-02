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
    val kakaoId: Long,
) {
    @Serializable
    data class TokenType(
        @SerialName("accessToken")
        val accessToken: TokenInfo,
        @SerialName("refreshToken")
        val refreshToken: TokenInfo,
    )

    @Serializable
    data class TokenInfo(
        @SerialName("token")
        val token: String,
        @SerialName("expiredAt")
        val expiredAt: Long,
        @SerialName("calculatedExpiredAt")
        val calculatedExpiredAt: Long,
    )
}