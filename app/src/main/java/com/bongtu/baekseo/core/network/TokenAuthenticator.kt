package com.bongtu.baekseo.core.network

import com.bongtu.baekseo.core.common.app.AppRestarter
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authRepository: AuthRepository,
    private val appRestarter: AppRestarter,
    private val tokenDataStore: TokenDataStore,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= MAX_RESPONSE_COUNT) return null

        synchronized(this) {
            val newAccessToken = runBlocking {
                tryReissueToken()
            } ?: return null

            return response.request.newBuilder()
                .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX $newAccessToken")
                .build()
        }
    }

    private suspend fun tryReissueToken(): String? {
        val refreshToken = tokenDataStore.getRefreshToken()
            ?: return handleReissueFailure()

        val result = authRepository.postTokenReissue(refreshToken)

        return result.fold(
            onSuccess = { tokenReissue ->
                tokenDataStore.setTokens(
                    accessToken = tokenReissue.accessToken,
                    refreshToken = tokenReissue.refreshToken,
                )
                tokenReissue.accessToken
            },
            onFailure = {
                handleReissueFailure()
            }
        )
    }

    private suspend fun handleReissueFailure(): String? {
        tokenDataStore.clearInfo()
        appRestarter.restartApp(isStartLogin = true)
        return null
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var current = response.priorResponse
        while (current != null) {
            count++
            current = current.priorResponse
        }
        return count
    }

    companion object {
        private const val MAX_RESPONSE_COUNT = 2
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}
