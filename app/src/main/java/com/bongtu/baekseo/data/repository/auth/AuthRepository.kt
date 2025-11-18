package com.bongtu.baekseo.data.repository.auth

import com.bongtu.baekseo.data.model.auth.SocialLogin
import com.bongtu.baekseo.data.model.auth.TokenReissue

interface AuthRepository {
    suspend fun postSocialLogin(
        oauthProvider: String,
        idToken: String,
    ): Result<SocialLogin>

    suspend fun postSignUp(
        oauthId: String,
        oauthProvider: String,
        memberName: String,
        memberBirthday: String,
        memberIncome: String,
    ): Result<SocialLogin>

    suspend fun postTokenReissue(
        refreshToken: String?,
    ): Result<TokenReissue>
}
