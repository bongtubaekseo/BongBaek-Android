package com.bongtu.baekseo.domain.usecase.auth

import com.bongtu.baekseo.core.local.datastore.ApiKeyDataStore
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.data.model.auth.KakaoLogin
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import javax.inject.Inject

class SetKakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStore: TokenDataStore,
    private val apiKeyDataStore: ApiKeyDataStore,
    private val usernameDataStore: UsernameDataStore,
) {
    suspend operator fun invoke(oauthProvider: String, idToken: String): Result<KakaoLogin> {
        return authRepository.postKakaoLogin(
            oauthProvider = oauthProvider,
            idToken = idToken,
        ).onSuccess { response ->
                tokenDataStore.setTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
                usernameDataStore.setUsername(response.name)
                apiKeyDataStore.setApiKey(response.apiKey)
            }
    }
}