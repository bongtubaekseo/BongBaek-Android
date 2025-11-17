package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSocialLoginRequest(
    @SerialName("idToken")
    val idToken: String,
)
