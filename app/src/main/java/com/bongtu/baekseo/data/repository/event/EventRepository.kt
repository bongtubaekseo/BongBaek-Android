package com.bongtu.baekseo.data.repository.event

import com.bongtu.baekseo.data.dto.event.DeleteRecordRequest
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
import com.bongtu.baekseo.data.model.event.Cost
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import kotlinx.collections.immutable.ImmutableList

interface EventRepository {
    suspend fun postEventInfo(
        host: Host,
        event: Event,
        location: Location,
        highAccuracy: HighAccuracy,
    ): Result<Unit>

    suspend fun postEventCost(
        event: Event,
        location: Location,
        highAccuracy: HighAccuracy,
    ): Result<Cost>

    suspend fun getHomeEvents(): Result<ImmutableList<HomeEvent>>

    suspend fun getScheduleEvents(
        page: Int,
        category: String? = null,
    ): Result<PageScheduleEvent>

    suspend fun putEventInfo(
        eventId: String,
        host: Host,
        event: Event,
        location: Location,
    ): Result<Unit>

    suspend fun deleteEvents(
        request: DeleteRecordRequest,
    ): Result<Unit>

    suspend fun getRecordEvents(
        page: Int,
        attended: Boolean = true,
        category: String? = null,
    ): Result<PageScheduleEvent>
}
