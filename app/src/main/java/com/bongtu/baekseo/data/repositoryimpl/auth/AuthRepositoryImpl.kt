package com.bongtu.baekseo.data.repositoryimpl.auth

import com.bongtu.baekseo.data.datasource.auth.AuthDataSource
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.auth.KakaoLogin
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
                accessToken = accessToken
            )
        )
    }.mapCatching { response ->
        response.data.toModel()
    }
}