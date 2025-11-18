package com.bongtu.baekseo.data.repositoryimpl.auth

import com.bongtu.baekseo.data.datasource.auth.AuthDataSource
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostSocialLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.model.auth.SocialLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun postSocialLogin(
        oauthProvider: String,
        idToken: String,
    ): Result<SocialLogin> = runCatching {
        authDataSource.postSocialLogin(
            oauthProvider = oauthProvider,
            request = PostSocialLoginRequest(
                idToken = idToken,
            ),
        )
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun postKakaoLogin(
        oauthProvider: String,
        idToken: String,
    ): Result<KakaoLogin> = runCatching {
        authDataSource.postKakaoLogin(
            oauthProvider = oauthProvider,
            request = PostKakaoLoginRequest(
                idToken = idToken,
            )
        )
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun postSignUp(
        oauthId: String,
        oauthProvider: String,
        memberName: String,
        memberBirthday: String,
        memberIncome: String,
    ): Result<KakaoLogin> = runCatching {
        authDataSource.postSignUp(
            request = PostSignUpRequest(
                oauthId = oauthId,
                oauthProvider = oauthProvider,
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
            if (response.status == 200)
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
}
