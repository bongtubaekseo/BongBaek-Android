package com.bongtu.baekseo.data.datasourceimpl.auth

import com.bongtu.baekseo.data.datasource.auth.AuthDataSource
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.service.auth.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postKakaoLogin(request: PostKakaoLoginRequest) =
        authService.postKakaoLogin(request)

    override suspend fun postSignUp(request: PostSignUpRequest) =
        authService.postSignUp(request)

    override suspend fun postTokenReissue(request: PostTokenReissueRequest) =
        authService.postTokenReissue(request)
}