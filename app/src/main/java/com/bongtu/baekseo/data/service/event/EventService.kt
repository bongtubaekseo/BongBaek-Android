package com.bongtu.baekseo.data.service.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.event.GetHomeEventsResponse
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PutEventInfoRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {
    @POST("/api/v1/events")
    suspend fun postEventInfo(
        @Body request: PostEventInfoRequest,
    ): BaseResponse<Unit>

    @POST("/api/v1/events/cost")
    suspend fun postEventCost(
        @Body request: PostEventCostRequest,
    ): BaseResponse<PostEventCostResponse>

    @GET("api/v1/events/home")
    suspend fun getHomeEvents(): BaseResponse<GetHomeEventsResponse>

    @GET("api/v1/events/upcoming/{page}")
    suspend fun getScheduleEvents(
        @Path("page") page: Int,
        @Query("category") category: String? = null,
    ): BaseResponse<GetScheduleEventsResponse>
    suspend fun fetchHomeEvents() : BaseResponse<FetchHomeEventsResponse>

    @PUT("/api/v1/events/{eventId}")
    suspend fun putEventInfo(
        @Path("eventId") eventId: String,
        @Body request: PutEventInfoRequest,
    ): BaseResponse<Unit>
}