package com.bongtu.baekseo.data.model.event

data class HomeEvent(
    val eventId: String,
    val hostName: String,
    val hostNickname: String,
    val eventCategory: String,
    val relationship: String,
    val cost: Int,
    val eventDate: String,
    val dDay: Int,
    val location: String,
)