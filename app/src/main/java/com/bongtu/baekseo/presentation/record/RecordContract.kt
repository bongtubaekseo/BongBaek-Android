package com.bongtu.baekseo.presentation.record

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

class RecordContract {
    @Immutable
    data class RecordUiState(
        val recordLoadState: UiState<List<RecordEvent>> = UiState.Loading,
        val selectedDeleteEventIds: Set<String> = emptySet(),
        val attendType: AttendType = AttendType.ATTEND,
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val isDeleteMode: Boolean = false,
    )

    sealed class RecordSideEffect {
        data class NavigateToDetail(
            val eventId: String,
        ) : RecordSideEffect()

        data object NavigateToAdd : RecordSideEffect()
    }
}