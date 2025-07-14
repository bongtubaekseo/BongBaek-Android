package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType

class OnBoardingContract {
    @Immutable
    data class OnBoardingState(
        val kakaoLoginState: SocialLoginState = SocialLoginState.Idle,
    )

    @Immutable
    data class OnBoardingUiState(
        val loadState: UiState<Nothing> = UiState.Empty,
        val kakaoId: Long = 0L,
        val name: String = "",
        val birth: String = "",
        var dialogBirth: String = "",
        val income: String = IncomeType.NONE.label,
    )

    sealed class OnBoardingSideEffect {
        data object NavigateToHome : OnBoardingSideEffect()
    }

    sealed class SocialLoginState {
        data object Success : SocialLoginState()
        data object Fail : SocialLoginState()
        data object Idle : SocialLoginState()
    }
}