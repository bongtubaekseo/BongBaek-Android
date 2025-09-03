package com.bongtu.baekseo.domain.usecase.auth

import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        withdrawalReason: String,
        detail: String?,
    ): Result<Unit> = runCatching {
        val accessToken =
            tokenDataStore.getAccessToken() ?: throw NoSuchElementException("accessToken 없음")

        authRepository.postWithdraw(
            accessToken = accessToken,
            withdrawalReason = withdrawalReason,
            detail = detail,
        )
    }
}