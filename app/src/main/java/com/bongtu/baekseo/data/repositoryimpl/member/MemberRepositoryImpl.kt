package com.bongtu.baekseo.data.repositoryimpl.member

import com.bongtu.baekseo.data.datasource.member.MemberDataSource
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import com.bongtu.baekseo.data.dto.member.ProfileInfoDto
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.profile.ProfileInfo
import com.bongtu.baekseo.data.repository.member.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataSource: MemberDataSource,
) : MemberRepository {
    override suspend fun postLogout(): Result<Unit> = runCatching {
        memberDataSource.postLogout()
    }

    override suspend fun postWithdraw(
        withdrawalReason: String,
        detail: String?,
    ): Result<Unit> = runCatching {
        memberDataSource.postWithdraw(
            request = PostWithdrawRequest(
                withdrawalReason = withdrawalReason,
                detail = detail,
            )
        )
    }

    override suspend fun getProfileInfo(): Result<ProfileInfo> = runCatching {
        memberDataSource.getProfileInfo()
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun putProfileInfo(
        profileInfo: ProfileInfo
    ): Result<Unit> = runCatching {
        memberDataSource.putProfileInfo(
            request = ProfileInfoDto(
                memberName = profileInfo.memberName,
                memberBirthday = profileInfo.memberBirthday,
                memberIncome = profileInfo.memberIncome,
            ),
        )
    }
}