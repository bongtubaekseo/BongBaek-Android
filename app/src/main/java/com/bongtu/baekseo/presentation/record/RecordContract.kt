package com.bongtu.baekseo.presentation.record

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class RecordContract {
    @Immutable
    data class RecordUiState(
        val recordLoadState: UiState<Unit> = UiState.Loading,
        val scheduleList: ImmutableList<ScheduleEvent> = persistentListOf(),
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val attendType: AttendType = AttendType.ATTEND,
        val selectedDeleteEventIds: Set<String> = emptySet(),
        val isDeleteMode: Boolean = false,
    )

    sealed class RecordSideEffect {
        data class NavigateToDetail(
            val eventId: String,
        ) : RecordSideEffect()

        data object NavigateToAdd : RecordSideEffect()
    }
}