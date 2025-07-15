package com.bongtu.baekseo.presentation.detail

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.DetailEvent

class DetailContract {
    @Immutable
    data class DetailUiState(
        val loadState: UiState<DetailEvent> = UiState.Loading,
    )
}