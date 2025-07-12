package com.bongtu.baekseo.data.repositoryimpl.event

import com.bongtu.baekseo.data.datasource.event.EventDataSource
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.mapper.toDto
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.event.Cost
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.repository.event.EventRepository
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
}
