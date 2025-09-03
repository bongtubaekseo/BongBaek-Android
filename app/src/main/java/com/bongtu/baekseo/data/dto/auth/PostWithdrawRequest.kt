package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostWithdrawRequest(
    @SerialName("withdrawalReason")
    val withdrawalReason: String,
    @SerialName("detail")
    val detail: String? = null,
)
