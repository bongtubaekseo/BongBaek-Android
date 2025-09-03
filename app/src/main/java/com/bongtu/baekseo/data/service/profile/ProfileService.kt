package com.bongtu.baekseo.data.service.profile

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.profile.ProfileInfoDto
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileService {
    @GET("/api/v1/member/profile")
    suspend fun getProfileInfo(): BaseResponse<ProfileInfoDto>

    @PUT("/api/v1/member₩/profile")
    suspend fun putProfileInfo(profileInfo: ProfileInfoDto): BaseResponse<Unit>
}