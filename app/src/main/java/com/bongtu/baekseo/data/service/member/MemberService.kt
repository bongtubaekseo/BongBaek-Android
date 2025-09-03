package com.bongtu.baekseo.data.service.member

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface MemberService {
    @POST("/api/v1/member/logout")
    suspend fun postLogout(): BaseResponse<Unit>

    @POST("/api/v1/member/withdraw")
    suspend fun postWithdraw(
        @Body request: PostWithdrawRequest,
    ): BaseResponse<Unit>
}