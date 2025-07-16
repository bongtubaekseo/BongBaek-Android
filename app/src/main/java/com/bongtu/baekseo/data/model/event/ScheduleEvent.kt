package com.bongtu.baekseo.data.model.event

import kotlinx.collections.immutable.ImmutableList

data class ScheduleEvent(
    val eventId: String,
    val hostName: String,
    val hostNickname: String,
    val eventCategory: String,
    val relationship: String,
    val cost: Int,
    val eventDate: String,
)

data class PageScheduleEvent(
    val events: ImmutableList<ScheduleEvent>,
    val currentPage: Int,
    val isLast: Boolean,
)