package com.bongtu.baekseo.data.service

import com.bongtu.baekseo.data.dto.GetUserListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DummyService {
    @GET("/api/users")
    suspend fun getUserList(
        @Header("x-api-key") apiKey: String = "reqres-free-v1",
        @Query("page") page: Int,
    ): GetUserListResponse
}
