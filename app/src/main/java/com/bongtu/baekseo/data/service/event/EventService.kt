package com.bongtu.baekseo.data.service.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EventService {
    @POST("/api/v1/events")
    suspend fun postEventInfo(
        @Body request: PostEventInfoRequest,
    ): BaseResponse<Unit>

    @POST("/api/v1/events/cost")
    suspend fun postEventCost(
        @Body request: PostEventCostRequest,
    ): BaseResponse<PostEventCostResponse>
}