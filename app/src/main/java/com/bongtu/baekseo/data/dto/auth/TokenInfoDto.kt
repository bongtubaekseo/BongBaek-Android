package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    @SerialName("token")
    val token: String,
    @SerialName("expiredAt")
    val expiredAt: Long,
    @SerialName("calculatedExpiredAt")
    val calculatedExpiredAt: Long,
)