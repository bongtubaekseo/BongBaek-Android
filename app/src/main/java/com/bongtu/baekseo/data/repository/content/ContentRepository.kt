package com.bongtu.baekseo.data.repository.content

import com.bongtu.baekseo.data.model.content.HomeContent
import kotlinx.collections.immutable.ImmutableList

interface ContentRepository {
    suspend fun getHomeContents(): Result<ImmutableList<HomeContent>>
}
