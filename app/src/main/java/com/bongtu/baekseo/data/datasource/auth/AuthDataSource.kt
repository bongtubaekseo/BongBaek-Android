package com.bongtu.baekseo.data.datasource.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse

interface AuthDataSource {
    suspend fun postKakaoLogin(
        oauthProvider: String,
        request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    suspend fun postSignUp(
        request: PostSignUpRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    suspend fun postTokenReissue(
        request: PostTokenReissueRequest,
    ): BaseResponse<PostTokenReissueResponse>
}