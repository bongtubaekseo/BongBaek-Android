package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchHomeEventsResponse(
    @SerialName("events")
    val events: List<Event>
) {
    @Serializable
    data class Event(
        @SerialName("eventId")
        val eventId: String,
        @SerialName("hostInfo")
        val hostInfo: HostInfoDto,
        @SerialName("eventInfo")
        val eventInfo: EventInfo,
        @SerialName("locationInfo")
        val locationInfo: LocationInfo,
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
        @SerialName("dDay")
        val dDay: Int,
        @SerialName("note")
        val note: String?,
    )

    @Serializable
    data class LocationInfo(
        @SerialName("location")
        val location: String,
    )
}
