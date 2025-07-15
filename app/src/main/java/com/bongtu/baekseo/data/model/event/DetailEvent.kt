package com.bongtu.baekseo.data.model.event

data class DetailEvent(
    val eventId: String,
    val hostName: String,
    val hostNickname: String,
    val eventCategory: String,
    val relationship: String,
    val cost: Int,
    val isEventParticipated: Boolean,
    val eventDate: String,
    val note: String?,
    val locationInfo: Location?,
)