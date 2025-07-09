package com.bongtu.baekseo.presentation.home.model

import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import java.time.LocalDate

data class HomeEvent(
    val eventId: String,
    val hostInfo: HomeHostInfo,
    val eventInfo: HomeEventInfo,
    val locationInfo: HomeLocationInfo,
)

data class HomeHostInfo(
    val hostName: String,
    val hostNickname: String,
)

data class HomeEventInfo(
    val eventCategory: EventType,
    val relationship: RelationType,
    val cost: Int,
    val eventDate: LocalDate,
    val dDay: Int = 1,
)

data class HomeLocationInfo(
    val location: String,
)