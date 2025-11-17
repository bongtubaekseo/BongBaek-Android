package com.bongtu.baekseo.data.service.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostSocialLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostSocialLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/oauth/{oauthProvider}")
    suspend fun postSocialLogin(
        @Body request: PostSocialLoginRequest,
    ): BaseResponse<PostSocialLoginResponse>

    @POST("/api/v1/oauth/kakao")
    suspend fun postKakaoLogin(
        @Body request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    @POST("/api/v1/member/profile")
    suspend fun postSignUp(
        @Body request: PostSignUpRequest,
    ): BaseResponse<PostKakaoLoginResponse>

    @POST("/api/v1/member/reissue")
    suspend fun postTokenReissue(
        @Body request: PostTokenReissueRequest,
    ): BaseResponse<PostTokenReissueResponse>
}
