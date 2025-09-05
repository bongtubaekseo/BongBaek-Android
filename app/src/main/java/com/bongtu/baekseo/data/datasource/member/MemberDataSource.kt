package com.bongtu.baekseo.data.datasource.member

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.core.network.model.BaseResponseWithoutData
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import com.bongtu.baekseo.data.dto.member.ProfileInfoDto

interface MemberDataSource {
    suspend fun postLogout(): BaseResponseWithoutData

    suspend fun postWithdraw(
        request: PostWithdrawRequest,
    ): BaseResponseWithoutData

    suspend fun getProfileInfo(): BaseResponse<ProfileInfoDto>

    suspend fun putProfileInfo(
        request: ProfileInfoDto,
    ): BaseResponseWithoutData
}