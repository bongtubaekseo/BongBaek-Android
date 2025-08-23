package com.bongtu.baekseo.presentation.schedule.model

import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import java.time.LocalDate

data class ScheduleEvent(
    val eventId: String,
    val hostInfo: ScheduleHostInfo,
    val eventInfo: ScheduleEventInfo,
)

data class ScheduleHostInfo(
    val hostName: String,
    val hostNickname: String,
)

data class ScheduleEventInfo(
    val eventCategory: EventType,
    val relationship: RelationType,
    val cost: Int,
    val eventDate: LocalDate,
)