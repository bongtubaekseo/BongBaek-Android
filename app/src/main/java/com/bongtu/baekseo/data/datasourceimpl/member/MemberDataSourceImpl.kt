package com.bongtu.baekseo.data.datasourceimpl.member

import com.bongtu.baekseo.data.datasource.member.MemberDataSource
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import com.bongtu.baekseo.data.dto.profile.ProfileInfoDto
import com.bongtu.baekseo.data.service.member.MemberService
import javax.inject.Inject

class MemberDataSourceImpl @Inject constructor(
    private val memberService: MemberService,
) : MemberDataSource {

    override suspend fun postLogout() =
        memberService.postLogout()

    override suspend fun postWithdraw(
        request: PostWithdrawRequest,
    ) = memberService.postWithdraw(request)

    override suspend fun getProfileInfo() = memberService.getProfileInfo()

    override suspend fun putProfileInfo(
        request: ProfileInfoDto,
    ) = memberService.putProfileInfo(request)
}