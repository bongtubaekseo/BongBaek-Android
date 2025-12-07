package com.bongtu.baekseo.data.datasource.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.core.network.model.BaseResponseWithoutData
import com.bongtu.baekseo.data.dto.event.DeleteEventsRequest
import com.bongtu.baekseo.data.dto.event.GetEventDetailResponse
import com.bongtu.baekseo.data.dto.event.GetHomeEventsResponse
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PutEventInfoRequest

interface EventDataSource {
    suspend fun postEventInfo(
        request: PostEventInfoRequest,
    ): BaseResponseWithoutData

    suspend fun postEventCost(
        request: PostEventCostRequest,
    ): BaseResponse<PostEventCostResponse>

    suspend fun getEventDetail(
        eventId: String,
    ): BaseResponse<GetEventDetailResponse>

    suspend fun getHomeEvents(): BaseResponse<GetHomeEventsResponse>

    suspend fun getScheduleEvents(
        page: Int,
        category: String?,
    ): BaseResponse<GetScheduleEventsResponse>

    suspend fun putEventInfo(
        eventId: String,
        request: PutEventInfoRequest,
    ): BaseResponseWithoutData

    suspend fun deleteEvents(
        request: DeleteEventsRequest,
    ): BaseResponseWithoutData

    suspend fun getRecordEvents(
        page: Int,
        year: Int,
        month: Int,
        attended: Boolean,
        category: String?,
    ): BaseResponse<GetScheduleEventsResponse>

    suspend fun deleteEventInfo(
        eventId: String,
    ): BaseResponseWithoutData
}
