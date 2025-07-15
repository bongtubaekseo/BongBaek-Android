package com.bongtu.baekseo.data.model.event

data class DetailEvent(
    val eventId: String,
    val hostInfo: Host,
    val eventInfo: DetailEventInfo,
    val locationInfo: Location,
) {
    data class DetailEventInfo(
        val eventType: String,
        val relationType: String,
        val cost: Int,
        val attendType: String,
        val eventDate: String,
        val note: String?,
    )
}