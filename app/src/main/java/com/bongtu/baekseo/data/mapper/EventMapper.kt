package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.event.EventInfoDto
import com.bongtu.baekseo.data.dto.event.GetHomeEventsResponse
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
import com.bongtu.baekseo.data.dto.event.HighAccuracyDto
import com.bongtu.baekseo.data.dto.event.HostInfoDto
import com.bongtu.baekseo.data.dto.event.LocationInfoDto
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse
import com.bongtu.baekseo.data.model.event.Cost
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import kotlinx.collections.immutable.toImmutableList

fun Host.toDto() = HostInfoDto(
    hostName = name,
    hostNickname = nickname,
)

fun Event.toDto() = EventInfoDto(
    eventCategory = eventType,
    relationship = relationType,
    cost = cost,
    isAttend = isEventParticipated,
    eventDate = eventDate,
    note = note,
)

fun Location.toDto() = LocationInfoDto(
    location = location,
    address = address,
    latitude = latitude,
    longitude = longitude,
)

fun HighAccuracy.toDto() = HighAccuracyDto(
    contactFrequency = contactFrequency,
    meetFrequency = meetFrequency,
)

fun PostEventCostResponse.toModel() = Cost(
    cost = cost,
    min = range.min,
    max = range.max,
)

fun GetHomeEventsResponse.Event.toModel() = HomeEvent(
    eventId = eventId,
    hostName = hostInfo.hostName,
    hostNickname = hostInfo.hostNickname,
    eventCategory = eventInfo.eventCategory,
    relationship = eventInfo.relationship,
    cost = eventInfo.cost,
    eventDate = eventInfo.eventDate,
    dDay = eventInfo.dDay,
    location = locationInfo.location,
)

fun GetScheduleEventsResponse.Event.toModel() = ScheduleEvent(
    eventId = eventId,
    hostName = hostInfo.hostName,
    hostNickname = hostInfo.hostNickname,
    eventCategory = eventInfo.eventCategory,
    relationship = eventInfo.relationship,
    cost = eventInfo.cost,
    eventDate = eventInfo.eventDate,
)

fun GetScheduleEventsResponse.toModel() = PageScheduleEvent(
    events = events.map {
        it.toModel()
    }.toImmutableList(),
    currentPage = currentPage,
    isLast = isLast,
)