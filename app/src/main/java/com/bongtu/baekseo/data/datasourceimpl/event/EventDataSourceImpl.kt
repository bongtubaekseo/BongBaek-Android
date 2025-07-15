package com.bongtu.baekseo.data.datasourceimpl.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.datasource.event.EventDataSource
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
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

    override suspend fun getHomeEvents() = eventService.getHomeEvents()
    override suspend fun getScheduleEvents(
        page: Int,
        category: String?,
    ): BaseResponse<GetScheduleEventsResponse> = eventService.getScheduleEvents(
        page = page,
        category = category,
    )
}
