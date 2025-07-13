package com.bongtu.baekseo.domain.usecase

import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class SetKakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(kakaoToken: String): Result<KakaoLogin> {
        return authRepository.postKakaoLogin(kakaoToken)
    }
}