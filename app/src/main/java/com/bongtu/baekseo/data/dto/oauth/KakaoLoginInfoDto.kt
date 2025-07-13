package com.bongtu.baekseo.data.dto.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginInfoDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("isCompletedSignUp")
    val isCompletedSignUp: Boolean,
    @SerialName("kakaoId")
    val kakaoId: Long,
)
