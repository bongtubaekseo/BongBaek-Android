package com.bongtu.baekseo.data.datasource

import com.bongtu.baekseo.data.dto.GetUserListResponse

interface DummyDataSource {
    suspend fun getDummyUserList(page: Int): GetUserListResponse
}
