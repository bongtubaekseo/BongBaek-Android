package com.bongtu.baekseo.data.repository.member

interface MemberRepository {
    suspend fun postLogout(): Result<Unit>

    suspend fun postWithdraw(
        withdrawalReason: String,
        detail: String?,
    ): Result<Unit>
}