package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostTokenReissueRequest (
    @SerialName("refreshToken")
    val refreshToken: String? = null,
)