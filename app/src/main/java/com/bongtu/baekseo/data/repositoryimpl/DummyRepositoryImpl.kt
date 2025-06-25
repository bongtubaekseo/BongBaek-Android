package com.bongtu.baekseo.data.repositoryimpl

import com.bongtu.baekseo.data.datasource.DummyDataSource
import com.bongtu.baekseo.data.mapper.toDummyUser
import com.bongtu.baekseo.data.model.DummyUser
import com.bongtu.baekseo.data.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource
) : DummyRepository {
    override suspend fun fetchDummyUserList(page: Int): Result<List<DummyUser>> = runCatching {
        val response = dummyDataSource.getDummyUserList(page)
        response.data?.map { it.toDummyUser() } ?: emptyList()
    }
}
