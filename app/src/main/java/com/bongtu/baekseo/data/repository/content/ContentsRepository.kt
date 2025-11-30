package com.bongtu.baekseo.data.repository.content

import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.data.model.content.Contents
import com.bongtu.baekseo.data.model.content.PagedContents
import kotlinx.collections.immutable.ImmutableList

interface ContentsRepository {
    suspend fun getHomeContents(): Result<ImmutableList<Contents>>

    suspend fun getContentsByPage(
        page: Int,
        category: String? = null,
    ): Result<PagedContents>

    suspend fun getContentsDetail(
        contentId: String,
    ): Result<ContentsDetail>
}
