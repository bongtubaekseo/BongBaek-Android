package com.bongtu.baekseo.data.datasource.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostSocialLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostSocialLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse

interface AuthDataSource {
    suspend fun postSocialLogin(
        oauthProvider: String,
        request: PostSocialLoginRequest,
    ): BaseResponse<PostSocialLoginResponse>

    suspend fun postSignUp(
        request: PostSignUpRequest,
    ): BaseResponse<PostSocialLoginResponse>

    suspend fun postTokenReissue(
        request: PostTokenReissueRequest,
    ): BaseResponse<PostTokenReissueResponse>
}
