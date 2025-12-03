package com.bongtu.baekseo.presentation.setting

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType

class SettingContract {
    @Immutable
    data class SettingUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val userName: String = "",
        val userBirth: String = "",
        val userIncome: IncomeType = IncomeType.NONE,
    )

    sealed class SettingSideEffect {
        data object RestartApp : SettingSideEffect()
    }
}
