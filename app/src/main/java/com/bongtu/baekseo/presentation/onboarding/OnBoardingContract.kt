package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.runtime.Immutable

class OnBoardingContract {
    @Immutable
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