package com.bongtu.baekseo.domain.usecase.auth

import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        val accessToken =
            tokenDataStore.getAccessToken() ?: throw NoSuchElementException("accessToken 없음")
        authRepository.postLogout(accessToken)
    }
}