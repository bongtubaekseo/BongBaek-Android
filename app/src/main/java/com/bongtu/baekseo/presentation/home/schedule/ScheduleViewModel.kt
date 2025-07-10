package com.bongtu.baekseo.presentation.home.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.presentation.home.schedule.ScheduleContract.ScheduleState
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEvent
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEventInfo
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleHostInfo
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleState())
    val uiState = _uiState.asStateFlow()

    fun fetchScheduleEvent() {
        // TODO: 전체 일정 조회 API 연동
        viewModelScope.launch {
            updateScheduleUiState(
                value = UiState.Success(
                    persistentListOf(
                        ScheduleEvent(
                            eventId = "1",
                            hostInfo = ScheduleHostInfo(
                                hostName = "공승준",
                                hostNickname = "초록승준",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.WEDDING,
                                relationship = RelationType.FRIEND,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 3, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "2",
                            hostInfo = ScheduleHostInfo(
                                hostName = "김종명",
                                hostNickname = "봉준호",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.FIRST_BD,
                                relationship = RelationType.NEIGHBOR,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 2, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "3",
                            hostInfo = ScheduleHostInfo(
                                hostName = "김헤정",
                                hostNickname = "메정",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.BIRTHDAY,
                                relationship = RelationType.ALUMNI,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 1, 11),
                            ),
                        ),
                    )
                )
            )
        }
    }

    private fun updateScheduleUiState(value: UiState<ImmutableList<ScheduleEvent>>) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    scheduleLoadState = value,
                )
            }
        }
    }

    fun updateEventType(eventCategoryType: EventCategoryType) =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    eventCategoryType = eventCategoryType,
                )
            }
        }
}