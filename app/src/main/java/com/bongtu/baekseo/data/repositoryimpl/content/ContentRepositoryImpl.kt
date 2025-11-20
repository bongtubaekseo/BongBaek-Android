package com.bongtu.baekseo.data.repositoryimpl.content

import com.bongtu.baekseo.data.datasource.content.ContentDataSource
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.content.HomeContent
import com.bongtu.baekseo.data.repository.content.ContentRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentDataSource: ContentDataSource,
) : ContentRepository {
    override suspend fun getHomeContents(): Result<ImmutableList<HomeContent>> = runCatching {
        contentDataSource.getHomeContents()
    }.mapCatching { response ->
        response.data.contents.map {
            it.toModel()
        }.toImmutableList()
    }.recoverCatching {
        emptyList<HomeContent>().toImmutableList()
    }
}