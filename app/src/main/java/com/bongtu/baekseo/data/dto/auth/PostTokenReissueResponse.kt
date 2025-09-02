package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostTokenReissueResponse(
    @SerialName("accessToken")
    val accessToken: TokenInfoDto,
    @SerialName("refreshToken")
    val refreshToken: TokenInfoDto,
)