package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.runtime.Immutable

class OnBoardingContract {
    @Immutable
    data class OnBoardingState(
        val kakaoLoginState: SocialLoginState = SocialLoginState.Idle)
    data class OnBoardingUiState(
        val name: String = "",
        val birth: String = "",
        val income: String = "",
    )

    sealed interface OnBoardingSideEffect {
        sealed class MainSideEffect : OnBoardingSideEffect {
            data object NavigateToHome : MainSideEffect()
        }
    }
}

sealed interface SocialLoginState {
    data object Success : SocialLoginState
    data object Fail : SocialLoginState
    data object Idle : SocialLoginState
}