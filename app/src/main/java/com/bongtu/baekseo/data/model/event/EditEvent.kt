package com.bongtu.baekseo.data.model.event

import kotlinx.serialization.Serializable

@Serializable
data class EditEvent(
    val eventId: String,
    val hostName: String,
    val hostNickname: String,
    val eventCategory: String,
    val relationship: String,
    val cost: Int,
    val isEventParticipated: Boolean,
    val eventDate: String,
    val note: String,
    val location: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
