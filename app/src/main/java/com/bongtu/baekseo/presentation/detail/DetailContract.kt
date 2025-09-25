package com.bongtu.baekseo.presentation.detail

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.EditEvent
import com.bongtu.baekseo.data.model.event.Location

class DetailContract {
    @Immutable
    data class DetailUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val eventId: String = "",
        val hostName: String = "",
        val hostNickname: String = "",
        val eventCategory: String = "",
        val relationship: String = "",
        val cost: Int = 0,
        val isEventParticipated: Boolean = false,
        val eventDate: String = "",
        val note: String? = null,
        val locationInfo: Location? = null,
    )

    sealed class DetailSideEffect {
        data object NavigateToRecord : DetailSideEffect()
        data class NavigateToEdit(val editEvent: EditEvent) : DetailSideEffect()
    }
}