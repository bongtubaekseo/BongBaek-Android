package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HighAccuracyDto(
    @SerialName("contactFrequency")
    val contactFrequency: Int,
    @SerialName("meetFrequency")
    val meetFrequency: Int,
)
