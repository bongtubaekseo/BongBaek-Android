package com.bongtu.baekseo.data.repositoryimpl.event

import com.bongtu.baekseo.data.datasource.event.EventDataSource
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PutEventInfoRequest
import com.bongtu.baekseo.data.mapper.toDto
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.event.Cost
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventDataSource: EventDataSource,
) : EventRepository {
    override suspend fun postEventInfo(
        host: Host,
        event: Event,
        location: Location,
        highAccuracy: HighAccuracy,
    ): Result<Unit> = runCatching {
        eventDataSource.postEventInfo(
            request = PostEventInfoRequest(
                hostInfo = host.toDto(),
                eventInfo = event.toDto(),
                locationInfo = location.toDto(),
                highAccuracy = highAccuracy.toDto(),
            )
        )
    }

    override suspend fun postEventCost(
        event: Event,
        location: Location,
        highAccuracy: HighAccuracy,
    ): Result<Cost> = runCatching {
        eventDataSource.postEventCost(
            request = PostEventCostRequest(
                category = event.eventType,
                relationship = event.relationType,
                attended = event.isEventParticipated,
                locationInfo = location.toDto(),
                highAccuracy = highAccuracy.toDto(),
            )
        )
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun getHomeEvents(): Result<ImmutableList<HomeEvent>> = runCatching {
        eventDataSource.getHomeEvents()
    }.mapCatching { response ->
        response.data.events.map {
            it.toModel()
        }.toImmutableList()
    }.recoverCatching {
        emptyList<HomeEvent>().toImmutableList()
    }

    override suspend fun getScheduleEvents(
        page: Int,
        category: String?,
    ): Result<PageScheduleEvent> = runCatching {
        eventDataSource.getScheduleEvents(
            page = page,
            category = category,
        )
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun putEventInfo(
        eventId: String,
        host: Host,
        event: Event,
        location: Location,
    ): Result<Unit> = runCatching {
        eventDataSource.putEventInfo(
            eventId = eventId,
            request = PutEventInfoRequest(
                hostInfo = host.toDto(),
                eventInfo = event.toDto(),
                locationInfo = location.toDto(),
            ),
        )
    }

    override suspend fun deleteEvents(eventIds: List<String>): Result<Unit> = runCatching {
        eventDataSource.deleteEvents(
            eventIds = eventIds,
        )
    }

    override suspend fun getRecordEvents(
        page: Int,
        attended: Boolean,
        category: String?
    ): Result<PageScheduleEvent> = runCatching {
        eventDataSource.getRecordEvents(
            page = page,
            attended = attended,
            category = category,
        )
    }.mapCatching { response ->
        response.data.toModel()
    }
}
