package com.bongtu.baekseo.data.datasourceimpl.member

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.datasource.member.MemberDataSource
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import com.bongtu.baekseo.data.service.member.MemberService
import javax.inject.Inject

class MemberDataSourceImpl @Inject constructor(
    private val memberService: MemberService,
) : MemberDataSource {

    override suspend fun postLogout(): BaseResponse<Unit> =
        memberService.postLogout()

    override suspend fun postWithdraw(
        request: PostWithdrawRequest,
    ): BaseResponse<Unit> =
        memberService.postWithdraw(
            request = request,
        )
}