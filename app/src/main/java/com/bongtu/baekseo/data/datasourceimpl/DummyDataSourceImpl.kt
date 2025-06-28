package com.bongtu.baekseo.data.datasourceimpl

import com.bongtu.baekseo.data.datasource.DummyDataSource
import com.bongtu.baekseo.data.dto.GetUserListResponse
import com.bongtu.baekseo.data.service.DummyService
import javax.inject.Inject

class DummyDataSourceImpl @Inject constructor(
    private val dummyService: DummyService,
) : DummyDataSource {
    override suspend fun getDummyUserList(page: Int): GetUserListResponse {
        return dummyService.getUserList(page = page)
    }
}
