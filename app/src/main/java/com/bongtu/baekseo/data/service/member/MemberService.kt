package com.bongtu.baekseo.data.service.member

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.core.network.model.BaseResponseWithoutData
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import com.bongtu.baekseo.data.dto.member.ProfileInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface MemberService {
    @POST("/api/v1/member/logout")
    suspend fun postLogout(): BaseResponseWithoutData

    @POST("/api/v1/member/withdraw")
    suspend fun postWithdraw(
        @Body request: PostWithdrawRequest,
    ): BaseResponseWithoutData

    @GET("/api/v1/member/profile")
    suspend fun getProfileInfo(): BaseResponse<ProfileInfoDto>

    @PUT("/api/v1/member/profile")
    suspend fun putProfileInfo(
        @Body request: ProfileInfoDto
    ): BaseResponseWithoutData
}