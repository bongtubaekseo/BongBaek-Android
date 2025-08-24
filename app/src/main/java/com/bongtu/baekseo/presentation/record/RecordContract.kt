package com.bongtu.baekseo.presentation.record

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.core.common.type.EventCategoryType

class RecordContract {
    @Immutable
    data class RecordUiState(
        val recordLoadState: UiState<PageScheduleEvent> = UiState.Loading,
        val selectedDeleteEventIds: Set<String> = emptySet(),
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val isDeleteMode: Boolean = false,
        val page: Int = 0,
        val isLast: Boolean = false,
        val attendType: AttendType = AttendType.ATTEND,
    )

    sealed class RecordSideEffect {
        data class NavigateToDetail(
            val eventId: String,
        ) : RecordSideEffect()

        data object NavigateToAdd : RecordSideEffect()
    }
}