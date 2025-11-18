package com.bongtu.baekseo.presentation.login

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.bongtu.baekseo.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.delay
import timber.log.Timber
import java.security.SecureRandom
import java.util.Base64

object GoogleLauncher {

    suspend fun startGoogleLogin(activity: Activity): String? {
        val credentialManager = CredentialManager.create(activity)

        // 구글 Credential 계정 있는 경우 사용
        val googleIdResult = getGoogleId(
            credentialManager = credentialManager,
            context = activity,
        )

        googleIdResult
            .onSuccess { token ->
                Timber.tag("GoogleLogin").d("Credentials 로그인 성공")
                return token
            }
            .onFailure { e ->
                Timber.tag("GoogleLogin").d(e, "Credentials 로그인 실패")

                when (e) {
                    // 사용자가 취소 한 경우
                    is GetCredentialCancellationException -> {
                        Timber.tag("GoogleLogin").d(e, "Credentials 로그인 취소")
                        return null
                    }

                    // 로그인 된 구글 계정이 없는 경우
                    is NoCredentialException -> {
                        Timber.tag("GoogleLogin").d(e, "Credentials 로그인 실패")
                    }
                }
            }

        // 계정이 없는 경우 구글 로그인
        val signInWithGoogleResult = getSignInWithGoogle(
            credentialManager = credentialManager,
            context = activity,
        )

        signInWithGoogleResult
            .onSuccess { token ->
                Timber.tag("GoogleLogin").d("구글 로그인 성공")
                return token
            }
            .onFailure { e ->
                Timber.tag("GoogleLogin").d(e, "구글 로그인 실패")
                return null
            }
        return null
    }

    private suspend fun getGoogleId(
        credentialManager: CredentialManager,
        context: Context,
    ): Result<String> {
        val option = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .setNonce(generateSecureRandomNonce())
            .build()

        return fetchIdToken(
            credentialManager = credentialManager,
            context = context,
            option = option,
        )
    }

    private suspend fun getSignInWithGoogle(
        credentialManager: CredentialManager,
        context: Context,
    ): Result<String> {
        val option = GetSignInWithGoogleOption.Builder(
            BuildConfig.GOOGLE_WEB_CLIENT_ID
        )
            .setNonce(generateSecureRandomNonce())
            .build()

        return fetchIdToken(
            credentialManager = credentialManager,
            context = context,
            option = option,
        )
    }

    private suspend fun fetchIdToken(
        credentialManager: CredentialManager,
        context: Context,
        option: CredentialOption,
    ): Result<String> = runCatching {
        //using delay() here helps prevent NoCredentialException when the BottomSheet Flow is triggered
        delay(250)

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(option)
            .build()

        val result = credentialManager.getCredential(
            request = request,
            context = context
        )

        val credential = result.credential

        val googleIdTokenCredential =
            GoogleIdTokenCredential.createFrom(credential.data)

        val idToken = googleIdTokenCredential.idToken
        idToken
    }.onFailure { e ->
        when (e) {
            is GetCredentialCancellationException ->
                Timber.tag("GoogleLogin").d("Sign-in was cancelled")

            is NoCredentialException ->
                Timber.tag("GoogleLogin").d("No credentials found")

            is GoogleIdTokenParsingException ->
                Timber.tag("GoogleLogin").d("Issue with parsing received GoogleIdToken")

            is GetCredentialCustomException ->
                Timber.tag("GoogleLogin").d("Issue with custom credential request")

            is GetCredentialException ->
                Timber.tag("GoogleLogin").d("Failure getting credentials")

            else ->
                Timber.tag("GoogleLogin").d("Unknown error")
        }
    }

    //This function is used to generate a secure nonce to pass in with our request
    private fun generateSecureRandomNonce(byteLength: Int = 32): String {
        val randomBytes = ByteArray(byteLength)
        SecureRandom.getInstanceStrong().nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }
}
