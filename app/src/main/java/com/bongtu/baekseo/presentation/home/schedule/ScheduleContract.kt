package com.bongtu.baekseo.presentation.home.schedule

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEvent
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

class ScheduleContract {
    @Immutable
    data class ScheduleState(
        val scheduleLoadState: UiState<List<ScheduleEvent>> = UiState.Loading,
        val eventCategoryType: EventCategoryType = EventCategoryType.ALL,
    )
}