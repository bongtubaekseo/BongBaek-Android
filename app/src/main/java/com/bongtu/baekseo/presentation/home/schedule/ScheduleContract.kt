package com.bongtu.baekseo.presentation.home.schedule

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ScheduleContract {
    @Immutable
    data class ScheduleState(
        val scheduleLoadState: UiState<ImmutableList<ScheduleEvent>> = UiState.Loading,
        val eventList: ImmutableList<ScheduleEvent> = persistentListOf(),
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
        val name: String = "",
        val page: Int = 0,
        val isLastPage: Boolean = false,
    )
}