package com.bongtu.baekseo.data.datasourceimpl.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.datasource.auth.AuthDataSource
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse
import com.bongtu.baekseo.data.dto.member.PostWithdrawRequest
import com.bongtu.baekseo.data.service.auth.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postKakaoLogin(request: PostKakaoLoginRequest): BaseResponse<PostKakaoLoginResponse> =
        authService.postKakaoLogin(request)

    override suspend fun postSignUp(request: PostSignUpRequest): BaseResponse<PostKakaoLoginResponse> =
        authService.postSignUp(request)

    override suspend fun postTokenReissue(request: PostTokenReissueRequest): BaseResponse<PostTokenReissueResponse> =
        authService.postTokenReissue(request)
}