package com.bongtu.baekseo.presentation.record

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.data.model.RecordEventItem
import com.bongtu.baekseo.presentation.record.type.AttendType
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

class RecordContract {
    @Immutable
    data class RecordState(
        val recordLoadState: UiState<List<RecordEventItem>> = UiState.Loading,
        val attendType: AttendType = AttendType.ATTEND,
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val isDeleting: Boolean = false,
    )
}
