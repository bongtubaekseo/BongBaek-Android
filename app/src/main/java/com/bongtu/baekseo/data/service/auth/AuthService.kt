package com.bongtu.baekseo.data.service.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostSignUpRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueRequest
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse
import com.bongtu.baekseo.data.dto.auth.PostWithdrawRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
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

    @POST("/api/v1/member/logout")
    suspend fun postLogout(
        @Header("Authorization") accessToken: String,
    ): BaseResponse<Unit>

    @POST("/api/v1/member/withdraw")
    suspend fun postWithdraw(
        @Header("Authorization") accessToken: String,
        @Body request: PostWithdrawRequest,
    ): BaseResponse<Unit>
}