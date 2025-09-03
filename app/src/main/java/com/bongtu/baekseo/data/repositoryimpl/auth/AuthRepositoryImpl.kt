package com.bongtu.baekseo.data.repositoryimpl.auth

import com.bongtu.baekseo.data.datasource.auth.AuthDataSource
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.dto.auth.PostWithdrawRequest
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postKakaoLogin(
        accessToken: String,
    ): Result<KakaoLogin> = runCatching {
        authDataSource.postKakaoLogin(
            request = PostKakaoLoginRequest(
                accessToken = accessToken,
            )
        )
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun postSignUp(
        kakaoId: Long,
        memberName: String,
        memberBirthday: String,
        memberIncome: String,
    ): Result<KakaoLogin> = runCatching {
        authDataSource.postSignUp(
            request = PostSignUpRequest(
                kakaoId = kakaoId,
                memberName = memberName,
                memberBirthday = memberBirthday,
                memberIncome = memberIncome,
            )
        )
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun postTokenReissue(refreshToken: String?): Result<TokenReissue> =
        runCatching {
            val response = authDataSource.postTokenReissue(
                request = PostTokenReissueRequest(
                    refreshToken = refreshToken,
                )
            )
            if (response.success || response.status == 200)
                response.data.toModel()
            else
                throw IllegalStateException("토큰 재발급 실패: ${response.status} ${response.message}")
        }.recoverCatching { error ->
            if (error is retrofit2.HttpException) {
                val code = error.code()
                if (code == 401 || code == 404) {
                    throw IllegalStateException("리프레시 토큰이 유효하지 않거나 만료되었습니다.")
                }
                throw IllegalStateException("토큰 재발급 실패: $code", error)
            } else {
                throw error
            }
        }

    override suspend fun postLogout(accessToken: String?): Result<Unit> = runCatching {
        authDataSource.postLogout(accessToken)
    }

    override suspend fun postWithdraw(
        accessToken: String?,
        withdrawalReason: String,
        detail: String?,
    ): Result<Unit> = runCatching {
        authDataSource.postWithdraw(
            accessToken = accessToken,
            request = PostWithdrawRequest(
                withdrawalReason = withdrawalReason,
                detail = detail,
            )
        )
    }
}