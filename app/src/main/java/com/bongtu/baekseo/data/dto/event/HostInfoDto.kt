package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HostInfoDto(
    @SerialName("hostName")
    val hostName: String,
    @SerialName("hostNickname")
    val hostNickname: String,
)
