package com.bongtu.baekseo.presentation.record

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.data.model.Event
import com.bongtu.baekseo.presentation.record.type.AttendType

class RecordContract {
    @Immutable
    data class RecordState(
        val recordLoadState: UiState<List<Event>> = UiState.Loading,
        val attendType: String = AttendType.ATTEND.label,
        val eventType: String = EventType.ALL.label,
        val isDeleting: Boolean = false,
    )
}
