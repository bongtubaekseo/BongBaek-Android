package com.bongtu.baekseo.presentation.schedule

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

class ScheduleContract {
    @Immutable
    data class ScheduleState(
        val scheduleLoadState: UiState<PageScheduleEvent> = UiState.Loading,
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val name: String = "",
    )

    sealed class ScheduleSideEffect {
        data object NavigateToHome : ScheduleSideEffect()
        data class NavigateToDetail(val eventId: String) : ScheduleSideEffect()
        data object NavigateToEdit : ScheduleSideEffect()
    }
}