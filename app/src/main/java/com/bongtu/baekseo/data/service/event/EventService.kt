package com.bongtu.baekseo.data.service.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.event.PostHomeEventsResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventService {
    @POST("events")
    suspend fun postEventInfo(
        @Body request: PostEventInfoRequest,
    ): BaseResponse<Unit>

    @POST("events/cost")
    suspend fun postEventCost(
        @Body request: PostEventCostRequest,
    ): BaseResponse<PostEventCostResponse>

    @GET("api/v1/events/home")
    suspend fun postHomeEvents() : BaseResponse<PostHomeEventsResponse>
}