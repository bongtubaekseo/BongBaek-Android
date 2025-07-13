package com.bongtu.baekseo.data.datasourceimpl.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.datasource.auth.AuthDataSource
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.service.auth.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun postKakaoLogin(request: PostKakaoLoginRequest): BaseResponse<PostKakaoLoginResponse> =
        authService.postKakaoLogin(request)
}