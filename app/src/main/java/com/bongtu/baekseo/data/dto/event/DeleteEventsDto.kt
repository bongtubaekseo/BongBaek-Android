package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName

data class DeleteEventsDto(
    @SerialName("eventIds")
    val eventIds: List<String>,
)
