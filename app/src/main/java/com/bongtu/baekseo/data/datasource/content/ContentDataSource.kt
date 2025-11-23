package com.bongtu.baekseo.data.datasource.content

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse

interface ContentDataSource {
    suspend fun getHomeContents(): BaseResponse<GetHomeContentsResponse>
}
