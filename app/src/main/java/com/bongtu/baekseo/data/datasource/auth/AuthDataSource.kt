package com.bongtu.baekseo.data.datasource.auth

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginRequest
import com.bongtu.baekseo.data.dto.oauth.PostKakaoLoginResponse

interface AuthDataSource {
    suspend fun postKakaoLogin(
        request: PostKakaoLoginRequest,
    ): BaseResponse<PostKakaoLoginResponse>
}