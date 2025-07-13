package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.runtime.Immutable

class OnBoardingContract {
    @Immutable
    data class OnBoardingState(
        val kakaoLoginState: SocialLoginState = SocialLoginState.Idle,
    )

    @Immutable
    data class OnBoardingUiState(
        val kakaoId: Long = 0L,
        val name: String = "",
        val birth: String = "",
        var dialogBirth: String = "",
        val income: String = "",
    )

    sealed class OnBoardingSideEffect {
        data object NavigateToHome : OnBoardingSideEffect()
    }
}

sealed interface SocialLoginState {
    data object Success : SocialLoginState
    data object Fail : SocialLoginState
    data object Idle : SocialLoginState
}