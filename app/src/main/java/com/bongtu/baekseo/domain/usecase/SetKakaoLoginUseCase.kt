package com.bongtu.baekseo.domain.usecase

import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class SetKakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStore: TokenDataStore,
) {
    suspend operator fun invoke(kakaoToken: String): Result<KakaoLogin> {
        return authRepository.postKakaoLogin(kakaoToken)
            .onSuccess { response ->
                tokenDataStore.setTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
            }
    }
}