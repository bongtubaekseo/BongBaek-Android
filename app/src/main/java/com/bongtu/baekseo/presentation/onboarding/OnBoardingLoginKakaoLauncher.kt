package com.bongtu.baekseo.presentation.onboarding

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import timber.log.Timber

class OnBoardingLoginKakaoLauncher(
    private val context: Context,
    private val onTokenReceived: (String) -> Unit,
    private val onError: (Throwable) -> Unit,
) {
    fun startKakaoLogin() {
        // 카카오 계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                // Login Fail
                Timber.tag("KakaoLogin").d("카카오계정으로 로그인 실패")
                onError(error)
            } else if (token != null) {
                // Login Success
                Timber.tag("KakaoLogin").d("카카오계정으로 로그인 성공: ${token.accessToken}")
                onTokenReceived(token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오 계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)

                    // 카카오톡 로그인 실패
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우
                    // 의도적인 로그인 취소로 보고 카카오 계정으로 로그인 시도 없이 로그인 취소로 처리
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        onError(error)
                        return@loginWithKakaoTalk
                    }
                } else if (token != null) {
                    // 카카오톡으로 로그인 성공
                    Timber.tag("KakaoLogin").d("카카오계정으로 로그인 성공: ${token.accessToken}")
                    onTokenReceived(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}