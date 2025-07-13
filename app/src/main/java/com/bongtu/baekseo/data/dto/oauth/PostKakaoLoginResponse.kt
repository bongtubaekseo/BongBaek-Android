package com.bongtu.baekseo.data.dto.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginResponse (
    @SerialName("token")
    val token: TokenInfo? = null,
    @SerialName("isCompletedSignUp")
    val isCompletedSignUp: Boolean,
    @SerialName("kakaoId")
    val kakaoId: Long,
) {
    @Serializable
    data class TokenInfo(
        @SerialName("accessToken")
        val accessToken: String,
        @SerialName("refreshToken")
        val refreshToken: String,
    )
}