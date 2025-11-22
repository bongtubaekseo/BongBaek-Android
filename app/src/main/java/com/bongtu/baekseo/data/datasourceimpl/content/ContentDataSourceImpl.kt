package com.bongtu.baekseo.data.datasourceimpl.content

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.datasource.content.ContentDataSource
import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse
import com.bongtu.baekseo.data.service.content.ContentService
import javax.inject.Inject

class ContentDataSourceImpl @Inject constructor(
    private val contentService: ContentService,
) : ContentDataSource {
    override suspend fun getHomeContents(): BaseResponse<GetHomeContentsResponse> =
        contentService.getHomeContents()
}
