package com.bongtu.baekseo.data.datasource.member

import com.bongtu.baekseo.core.network.model.BaseResponseWithoutData
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest

interface MemberDataSource {
    suspend fun postLogout(): BaseResponseWithoutData

    suspend fun postWithdraw(
        request: PostWithdrawRequest,
    ): BaseResponseWithoutData
}