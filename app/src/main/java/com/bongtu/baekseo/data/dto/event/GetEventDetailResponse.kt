package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetEventDetailResponse(
    @SerialName("eventId")
    val eventId: String,
    @SerialName("hostInfo")
    val hostInfo: HostInfoDto,
    @SerialName("eventInfo")
    val eventInfo: EventInfoDto,
    @SerialName("locationInfo")
    val locationInfo: LocationInfoDto,
)