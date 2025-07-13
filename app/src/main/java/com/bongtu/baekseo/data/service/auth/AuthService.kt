package com.bongtu.baekseo.data.service.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/oauth/kakao")
    suspend fun postKakaoLogin(
        @Body request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>
}