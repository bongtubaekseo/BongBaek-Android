package com.bongtu.baekseo.presentation.withdraw

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.WithdrawType

class WithdrawContract {
    @Immutable
    data class WithdrawUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val reasonType: WithdrawType? = null,
        val etcReason: String = "",
    )
}