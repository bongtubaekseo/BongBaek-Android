package com.bongtu.baekseo.domain.usecase.auth

import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class CheckAutoLoginUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        val refreshToken =
            tokenDataStore.getRefreshToken() ?: throw NoSuchElementException("refreshToken 없음")

        authRepository.postTokenReissue(refreshToken).onSuccess { response ->
            tokenDataStore.setTokens(response.accessToken, response.refreshToken)
        }.onFailure { error ->
            if (error is IllegalStateException) {
                tokenDataStore.clearInfo()
            }
            throw error
        }
    }
}