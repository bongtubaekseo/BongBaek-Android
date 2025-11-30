package com.bongtu.baekseo.data.datasource.content

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.content.GetContentsByPageResponse
import com.bongtu.baekseo.data.dto.content.GetContentsDetailResponse
import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse

interface ContentDataSource {
    suspend fun getHomeContents(): BaseResponse<GetHomeContentsResponse>

    suspend fun getContentsByPage(
        page: Int,
        category: String?,
    ): BaseResponse<GetContentsByPageResponse>

    suspend fun getContentsDetail(
        contentId: String,
    ): BaseResponse<GetContentsDetailResponse>
}
