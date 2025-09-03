package com.bongtu.baekseo.data.datasource.member

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest

interface MemberDataSource {
    suspend fun postLogout(): BaseResponse<Unit>

    suspend fun postWithdraw(
        request: PostWithdrawRequest,
    ): BaseResponse<Unit>
}