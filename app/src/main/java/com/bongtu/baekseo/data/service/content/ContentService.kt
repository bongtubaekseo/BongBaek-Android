package com.bongtu.baekseo.data.service.content

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse
import retrofit2.http.GET

interface ContentService {
    @GET("/api/v1/content/home")
    suspend fun getHomeContents(): BaseResponse<GetHomeContentsResponse>
}