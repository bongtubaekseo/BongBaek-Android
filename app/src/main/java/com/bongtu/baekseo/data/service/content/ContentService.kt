package com.bongtu.baekseo.data.service.content

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.content.GetContentsByPageResponse
import com.bongtu.baekseo.data.dto.content.GetContentsDetailResponse
import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentService {
    @GET("/api/v1/content/home")
    suspend fun getHomeContents(): BaseResponse<GetHomeContentsResponse>

    @GET("/api/v1/content/list/{page}")
    suspend fun getContentsByPage(
        @Path("page") page: Int,
        @Query("category") category: String?,
    ): BaseResponse<GetContentsByPageResponse>

    @GET("/api/v1/content/{contentId}")
    suspend fun getContentsDetail(
        @Path("contentId") contentId: String,
    ): BaseResponse<GetContentsDetailResponse>
}
