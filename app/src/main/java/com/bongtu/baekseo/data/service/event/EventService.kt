package com.bongtu.baekseo.data.service.event

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.event.DeleteRecordRequest
import com.bongtu.baekseo.data.dto.event.GetHomeEventsResponse
import com.bongtu.baekseo.data.dto.event.GetScheduleEventsResponse
import com.bongtu.baekseo.data.dto.event.PostEventCostRequest
import com.bongtu.baekseo.data.dto.event.PostEventCostResponse
import com.bongtu.baekseo.data.dto.event.PostEventInfoRequest
import com.bongtu.baekseo.data.dto.event.PutEventInfoRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @PUT("/api/v1/events/{eventId}")
    suspend fun putEventInfo(
        @Path("eventId") eventId: String,
        @Body request: PutEventInfoRequest,
    ): BaseResponse<Unit>

    @HTTP(method = "DELETE", path = "/api/v1/events", hasBody = true)
    suspend fun deleteEvents(
        @Body request: DeleteRecordRequest,
    ): BaseResponse<Unit>

    @GET("/api/v1/events/history/{page}")
    suspend fun getRecordEvents(
        @Path("page") page: Int,
        @Query("attended") attended: Boolean = true,
        @Query("category") category: String? = null,
    ): BaseResponse<GetScheduleEventsResponse>
}