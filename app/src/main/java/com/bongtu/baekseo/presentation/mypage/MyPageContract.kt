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
    )
}