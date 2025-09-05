package com.bongtu.baekseo.data.repository.member

import com.bongtu.baekseo.data.model.member.ProfileInfo

interface MemberRepository {
    suspend fun postLogout(): Result<Unit>

    suspend fun postWithdraw(
        withdrawalReason: String,
        detail: String?,
    ): Result<Unit>

    suspend fun getProfileInfo(): Result<ProfileInfo>

    suspend fun putProfileInfo(
        profileInfo: ProfileInfo,
    ): Result<Unit>
}