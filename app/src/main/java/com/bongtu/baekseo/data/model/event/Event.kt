package com.bongtu.baekseo.data.model.event

data class Event(
    val eventType: String,
    val relationType: String,
    val cost: Int,
    val isEventParticipated: Boolean,
    val eventDate: String,
)
