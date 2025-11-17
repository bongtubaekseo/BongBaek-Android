package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.auth.PostKakaoLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostSocialLoginResponse
import com.bongtu.baekseo.data.dto.auth.PostTokenReissueResponse
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.model.auth.SocialLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue

fun PostSocialLoginResponse.toModel() = SocialLogin(
    name = name.orEmpty(),
    accessToken = token?.accessToken?.token.orEmpty(),
    refreshToken = token?.refreshToken?.token.orEmpty(),
    isCompletedSignUp = isCompletedSignUp,
    oauthId = oauthId,
    apiKey = apiKey.orEmpty(),
)

fun PostKakaoLoginResponse.toModel() = KakaoLogin(
    name = name.orEmpty(),
    accessToken = token?.accessToken?.token.orEmpty(),
    refreshToken = token?.refreshToken?.token.orEmpty(),
    isCompletedSignUp = isCompletedSignUp,
    oauthId = oauthId,
    oauthProvider = oauthProvider,
    apiKey = apiKey.orEmpty(),
)

fun PostTokenReissueResponse.toModel() = TokenReissue(
    accessToken = accessToken.token,
    refreshToken = refreshToken.token,
)
