package com.bongtu.baekseo.data.repository.content

import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.data.model.content.HomeContent
import kotlinx.collections.immutable.ImmutableList

interface ContentsRepository {
    suspend fun getHomeContents(): Result<ImmutableList<HomeContent>>

    suspend fun getContentsByPage(
        page: Int,
        category: String,
    ): Result<ImmutableList<HomeContent>>

    suspend fun getContentsDetail(
        contentId: Int,
    ): Result<ContentsDetail>
}
