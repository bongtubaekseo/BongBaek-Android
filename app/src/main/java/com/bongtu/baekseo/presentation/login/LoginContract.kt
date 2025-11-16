package com.bongtu.baekseo.presentation.login

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState

class LoginContract {
    @Immutable
    data class LoginUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val oauthId: String = "",
    )

    sealed class LoginSideEffect {
        data object NavigateToHome : LoginSideEffect()
    }

}
