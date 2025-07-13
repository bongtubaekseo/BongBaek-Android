package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.model.auth.KakaoLogin

fun PostKakaoLoginResponse.toModel() = KakaoLogin(
    accessToken = token?.accessToken ?: "",
    refreshToken = token?.refreshToken ?: "",
    isCompletedSignUp = isCompletedSignUp,
    kakaoId = kakaoId,
)