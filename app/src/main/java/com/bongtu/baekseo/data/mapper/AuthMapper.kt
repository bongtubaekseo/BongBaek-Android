package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue

fun PostKakaoLoginResponse.toModel() = KakaoLogin(
    name = name.orEmpty(),
    accessToken = token?.accessToken.orEmpty(),
    refreshToken = token?.refreshToken.orEmpty(),
    isCompletedSignUp = isCompletedSignUp,
    kakaoId = kakaoId,
)

fun PostTokenReissueResponse.toModel() = TokenReissue(
    accessToken = accessToken,
    refreshToken = refreshToken,
)