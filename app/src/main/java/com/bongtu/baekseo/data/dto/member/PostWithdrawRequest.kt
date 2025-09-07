package com.bongtu.baekseo.data.dto.member

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostWithdrawRequest(
    @SerialName("withdrawalReason")
    val withdrawalReason: String,
    @SerialName("detail")
    val detail: String?,
)