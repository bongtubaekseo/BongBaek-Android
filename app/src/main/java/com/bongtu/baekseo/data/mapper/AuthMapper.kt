package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue

fun PostKakaoLoginResponse.toModel() = KakaoLogin(
    name = name.orEmpty(),
    accessToken = token?.accessToken?.token.orEmpty(),
    refreshToken = token?.refreshToken?.token.orEmpty(),
    isCompletedSignUp = isCompletedSignUp,
    kakaoId = kakaoId,
    apiKey = apiKey.orEmpty(),
)

fun PostTokenReissueResponse.toModel() = TokenReissue(
    accessToken = accessToken.token,
    refreshToken = refreshToken.token,
)