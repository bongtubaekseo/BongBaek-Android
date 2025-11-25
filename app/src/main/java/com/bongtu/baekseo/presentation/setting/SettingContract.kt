package com.bongtu.baekseo.presentation.setting

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType

class SettingContract {
    @Immutable
    data class SettingUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val userName: String = "",
        val nameError: String = "",
        val userBirth: String = "",
        val dialogBirth: String = "",
        val userIncome: IncomeType = IncomeType.NONE,
        val originalName: String = "",
        val originalBirth: String = "",
        val originalIncome: IncomeType = IncomeType.NONE,
    ) {
        val isFormValid: Boolean
            get() = userName.isNotEmpty() && userBirth.isNotEmpty() && nameError.isEmpty()

        val isProfileChanged: Boolean
            get() = userName != originalName ||
                    userBirth != originalBirth ||
                    userIncome != originalIncome

        val isEditButtonEnabled: Boolean
            get() = isFormValid && isProfileChanged && loadState !is UiState.Loading
    }

    sealed interface SettingSideEffect {
        sealed class MainSideEffect : SettingSideEffect {
            data object RestartApp : MainSideEffect()
        }

        sealed class ProfileEditSideEffect : SettingSideEffect {
            data object NavigateToSetting : ProfileEditSideEffect()
        }
    }
}