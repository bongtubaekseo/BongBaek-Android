package com.bongtu.baekseo.data.service

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PostEventInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EventService {
    @POST("events")
    suspend fun postEventInfo(
        @Body request: PostEventInfoRequest,
    ): BaseResponse<Unit>

    @POST("events/cost")
    suspend fun postEventCost(
        @Body request: PostEventInfoRequest,
    ): BaseResponse<PostEventInfoResponse>
}
