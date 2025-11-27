package com.bongtu.baekseo.data.repositoryimpl.content

import com.bongtu.baekseo.data.datasource.content.ContentDataSource
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.content.Contents
import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.data.model.content.PagedContents
import com.bongtu.baekseo.data.repository.content.ContentsRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import javax.inject.Inject

class ContentsRepositoryImpl @Inject constructor(
    private val contentDataSource: ContentDataSource,
) : ContentsRepository {
    override suspend fun getHomeContents(): Result<ImmutableList<Contents>> = runCatching {
        contentDataSource.getHomeContents()
    }.mapCatching { response ->
        response.data.contents.map {
            it.toModel()
        }.toImmutableList()
    }.recoverCatching {
        Timber.e(it)
        persistentListOf()
    }


    override suspend fun getContentsByPage(
        page: Int,
        category: String?,
    ): Result<PagedContents> = runCatching {
        contentDataSource.getContentsByPage(page, category)
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun getContentsDetail(
        contentId: String,
    ): Result<ContentsDetail> = runCatching {
        contentDataSource.getContentsDetail(contentId)
    }.mapCatching { response ->
        response.data.toModel()
    }
}
