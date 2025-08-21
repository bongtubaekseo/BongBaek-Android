package com.bongtu.baekseo.presentation.schedule

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ScheduleContract {
    @Immutable
    data class ScheduleState(
        val scheduleLoadState: UiState<Unit> = UiState.Loading,
        val scheduleList: ImmutableList<ScheduleEvent> = persistentListOf(),
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val name: String = "",
    )

    sealed class ScheduleSideEffect {
        data object NavigateToHome : ScheduleSideEffect()
        data class NavigateToDetail(val eventId: String) : ScheduleSideEffect()
        data object NavigateToEdit : ScheduleSideEffect()
    }
}