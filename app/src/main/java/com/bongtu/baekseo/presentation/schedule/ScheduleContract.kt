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
        val page: Int = 0,
        val isLast: Boolean = false,
    )
}