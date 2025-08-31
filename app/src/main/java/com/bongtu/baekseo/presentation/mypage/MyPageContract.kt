package com.bongtu.baekseo.presentation.mypage

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType

class MyPageContract {
    @Immutable
    data class MyPageUiState(
        val myPageLoadState: UiState<Unit> = UiState.Empty,
        val userName: String = "",
        val nameError: String = "",
        val userBirth: String = "",
        val dialogBirth: String = "",
        val userIncome: String = IncomeType.NONE.label,
        val originalName: String = "",
        val originalBirth: String = "",
        val originalIncome: String = IncomeType.NONE.label,
    ) {
        val isFormValid: Boolean
            get() = userName.isNotEmpty() && userBirth.isNotEmpty() && nameError.isEmpty()

        val isProfileChanged: Boolean
            get() = userName != originalName ||
                    userBirth != originalBirth ||
                    userIncome != originalIncome

        val isEditButtonEnabled: Boolean
            get() = isFormValid && isProfileChanged
    }
}