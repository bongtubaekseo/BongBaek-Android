package com.bongtu.baekseo.data.datasource.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.event.PostHomeEventsResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse

interface EventDataSource {
    suspend fun postEventInfo(
        request: PostEventInfoRequest,
    ): BaseResponse<Unit>

    suspend fun postEventCost(
        request: PostEventCostRequest,
    ): BaseResponse<PostEventCostResponse>

    suspend fun fetchHomeEvents(): BaseResponse<PostHomeEventsResponse>
}
