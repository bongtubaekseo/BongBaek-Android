package com.bongtu.baekseo.data.repository

import com.bongtu.baekseo.data.model.DummyUser

interface DummyRepository {
    suspend fun fetchDummyUserList(page: Int): Result<List<DummyUser>>
}
