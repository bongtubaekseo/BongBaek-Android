package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class EventInfoDto(
    @SerialName("eventCategory")
    val eventCategory: String,
    @SerialName("relationship")
    val relationship: String,
    @SerialName("cost")
    val cost: Int,
    @SerialName("isAttend")
    val isAttend: Boolean,
    @SerialName("eventDate")
    val eventDate: String,
)