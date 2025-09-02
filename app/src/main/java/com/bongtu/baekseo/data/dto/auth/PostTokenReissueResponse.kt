package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostTokenReissueResponse(
    @SerialName("accessToken")
    val accessToken: TokenInfo,
    @SerialName("refreshToken")
    val refreshToken: TokenInfo,
)