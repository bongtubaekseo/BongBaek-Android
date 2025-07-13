package com.bongtu.baekseo.data.repository.auth

import com.bongtu.baekseo.data.model.auth.KakaoLogin

interface AuthRepository {
    suspend fun postKakaoLogin(
        accessToken: String,
    ): Result<KakaoLogin>

    suspend fun postSignUp(
        kakaoId: Long,
        memberName: String,
        memberBirthday: String,
        memberIncome: String,
    ): Result<KakaoLogin>
}