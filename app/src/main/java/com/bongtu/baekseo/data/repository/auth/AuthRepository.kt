package com.bongtu.baekseo.data.repository.auth

import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue

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

    suspend fun postTokenReissue(
        refreshToken: String?,
    ): Result<TokenReissue>

    suspend fun postLogout(
        accessToken: String?,
    ): Result<Unit>
}