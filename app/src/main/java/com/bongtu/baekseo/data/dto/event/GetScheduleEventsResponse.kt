package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetScheduleEventsResponse(
    @SerialName("events")
    val events: List<Event>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("isLast")
    val isLast: Boolean,
) {
    @Serializable
    data class Event(
        @SerialName("eventId")
        val eventId: String,
        @SerialName("hostInfo")
        val hostInfo: HostInfoDto,
        @SerialName("eventInfo")
        val eventInfo: EventInfo,

    )

    @Serializable
    data class EventInfo(
        @SerialName("eventCategory")
        val eventCategory: String,
        @SerialName("relationship")
        val relationship: String,
        @SerialName("cost")
        val cost: Int,
        @SerialName("isAttend")
        val isAttend: Boolean?,
        @SerialName("eventDate")
        val eventDate: String,
    )
}
