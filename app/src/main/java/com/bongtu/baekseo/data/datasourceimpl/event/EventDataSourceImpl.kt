package com.bongtu.baekseo.data.datasourceimpl.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.datasource.event.EventDataSource
import com.bongtu.baekseo.data.dto.event.DeleteEventsRequest
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PutEventInfoRequest
import com.bongtu.baekseo.data.service.event.EventService
import javax.inject.Inject

class EventDataSourceImpl @Inject constructor(
    private val eventService: EventService,
) : EventDataSource {
    override suspend fun postEventInfo(
        request: PostEventInfoRequest,
    ) = eventService.postEventInfo(request)

    override suspend fun postEventCost(
        request: PostEventCostRequest,
    ) = eventService.postEventCost(request)

    override suspend fun putEventInfo(
        eventId: String,
        request: PutEventInfoRequest,
    ) = eventService.putEventInfo(eventId, request)

    override suspend fun deleteEvents(
        request: DeleteEventsRequest,
    ) = eventService.deleteEvents(request)

    override suspend fun getRecordEvents(
        page: Int,
        attended: Boolean,
        category: String?,
    ) = eventService.getRecordEvents(
        page = page,
        attended = attended,
        category = category,
    )

    override suspend fun getEventDetail(
        eventId: String,
    ) = eventService.getEventDetail(eventId)

    override suspend fun getHomeEvents() = eventService.getHomeEvents()

    override suspend fun getScheduleEvents(
        page: Int,
        category: String?,
    ) = eventService.getScheduleEvents(
        page = page,
        category = category,
    )

    override suspend fun deleteEventInfo(
        eventId: String,
    ) = eventService.deleteEventInfo(eventId)
}
