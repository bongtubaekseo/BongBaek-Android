package com.bongtu.baekseo.data.datasourceimpl.content

import com.bongtu.baekseo.data.datasource.content.ContentDataSource
import com.bongtu.baekseo.data.service.content.ContentService
import javax.inject.Inject

class ContentDataSourceImpl @Inject constructor(
    private val contentService: ContentService,
) : ContentDataSource {
    override suspend fun getHomeContents() = contentService.getHomeContents()

    override suspend fun getContentsByPage(
        page: Int,
        category: String?,
    ) = contentService.getContentsByPage(
        page = page,
        category = category,
    )

    override suspend fun getContentsDetail(
        contentId: String,
    ) = contentService.getContentsDetail(contentId)
}
