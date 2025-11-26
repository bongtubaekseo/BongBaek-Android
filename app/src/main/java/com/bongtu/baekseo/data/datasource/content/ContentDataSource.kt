package com.bongtu.baekseo.data.datasource.content

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.content.GetContentsByPage
import com.bongtu.baekseo.data.dto.content.GetContentsDetailResponse
import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse

interface ContentDataSource {
    suspend fun getHomeContents(): BaseResponse<GetHomeContentsResponse>

    suspend fun getContentsByPage(
        page: Int,
        category: String,
    ): BaseResponse<GetContentsByPage>

    suspend fun getContentsDetail(
        contentId: Int,
    ): BaseResponse<GetContentsDetailResponse>
}
