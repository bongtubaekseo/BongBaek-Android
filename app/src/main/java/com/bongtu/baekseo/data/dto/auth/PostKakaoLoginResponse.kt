package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginResponse (
    @SerialName("name")
    val name: String? = null,
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