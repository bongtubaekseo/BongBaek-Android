package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteEventsRequest(
    @SerialName("eventIds")
    val eventIds: List<String>,
)
