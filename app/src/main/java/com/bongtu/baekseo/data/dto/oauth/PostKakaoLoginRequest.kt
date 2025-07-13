package com.bongtu.baekseo.data.dto.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostKakaoLoginRequest (
    @SerialName("accessToken")
    val accessToken: String,
)