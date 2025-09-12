package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType

class OnBoardingContract {
    @Immutable
    data class OnBoardingUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val kakaoLoginState: SocialLoginState = SocialLoginState.Idle,
        val kakaoId: String = "",
        val name: String = "",
        val birth: String = "",
        var dialogBirth: String = "",
        val income: IncomeType = IncomeType.NONE,
        val nameError: String = "",
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