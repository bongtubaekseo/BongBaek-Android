package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteRecordRequest(
    @SerialName("eventIds")
    val eventIds: List<String>,
)
