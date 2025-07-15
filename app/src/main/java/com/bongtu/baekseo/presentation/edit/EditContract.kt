package com.bongtu.baekseo.presentation.edit

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.Location

class EditContract {
    @Immutable
    data class EditUiState(
        val submitState: UiState<Unit> = UiState.Empty,
        val hostName: String = "",
        val hostNickname: String = "",
        val eventCategory: String = "",
        val relationship: String = "",
        val cost: String = "",
        val attendLabel: String = "",
        val eventDate: String = "",
        val note: String = "",
        val locationInfo: Location? = null,
    )
}