package com.bongtu.baekseo.data.datasource.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest

interface AuthDataSource {
    suspend fun postKakaoLogin(
        request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    suspend fun postSignUp(
        request: PostSignUpRequest,
    ): BaseResponse<PostKakaoLoginResponse>
}