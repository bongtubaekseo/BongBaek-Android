package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.runtime.Immutable

class OnBoardingContract {
    @Immutable
    data class OnBoardingState(
        val kakaoLoginState: SocialLoginState = SocialLoginState.Idle,
    )
}

sealed interface SocialLoginState {
    data object Success : SocialLoginState
    data object Fail : SocialLoginState
    data object Idle : SocialLoginState
}