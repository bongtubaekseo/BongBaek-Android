package com.bongtu.baekseo.presentation.record

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.data.repository.DummyRepository
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState
import com.bongtu.baekseo.presentation.record.type.AttendType
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordState())
    val uiState = _uiState.asStateFlow()

    fun fetchRecordEvent() {
        // TODO("서버 통신 연결")
        updateRecordUiState(
            value = UiState.Success(
                listOf(
                    RecordEvent(
                        eventId = "201",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 5, 4),
                    ),
                    RecordEvent(
                        eventId = "202",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 5, 2),
                    ),
                    RecordEvent(
                        eventId = "203",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 4, 10),
                    ),
                    RecordEvent(
                        eventId = "204",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 2, 23),
                    ),
                    RecordEvent(
                        eventId = "205",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2024, 6, 8),
                    ),
                    RecordEvent(
                        eventId = "206",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2024, 5, 24),
                    ),
                    RecordEvent(
                        eventId = "207",
                        hostName = "username",
                        hostNickName = "nickname",
                        category = "경조사 유형",
                        relationship = "관계",
                        cost = 10000,
                        eventDate = LocalDate.of(2023, 2, 4),
                    ),
                ),
            ),
        )
    }

    fun fetchSelectedDeleteEventIds() {
        // TODO("선택된 삭제 처리 추가 예정")
        updateDeleteModeCancel()
        updateInitSelectedDeleteEventIds()
    }

    private fun updateRecordUiState(value: UiState<List<RecordEvent>>) =
        _uiState.update { currentState ->
            currentState.copy(
                recordLoadState = value,
            )
        }

    private fun updateInitSelectedDeleteEventIds() =
        _uiState.update { currentState ->
            currentState.copy(
                selectedDeleteEventIds = emptySet(),
            )
        }

    fun updateAttendType(attendType: AttendType) =
        _uiState.update { currentState ->
            currentState.copy(
                attendType = attendType,
            )
        }

    fun updateEventType(eventCategoryType: EventCategoryType) =
        _uiState.update { currentState ->
            currentState.copy(
                eventCategoryType = eventCategoryType,
            )
        }

    fun updateDeleteMode() =
        _uiState.update { currentState ->
            currentState.copy(
                isDeleteMode = true,
            )
        }

    fun updateDeleteModeCancel() =
        _uiState.update { currentState ->
            currentState.copy(
                isDeleteMode = false,
                selectedDeleteEventIds = emptySet(),
            )
        }

    fun updateSelectedDeleteEventId(eventId: String) =
        _uiState.update { currentState ->
            currentState.copy(
                selectedDeleteEventIds = if (currentState.selectedDeleteEventIds.contains(eventId)) {
                    currentState.selectedDeleteEventIds - eventId
                } else {
                    currentState.selectedDeleteEventIds + eventId
                }
            )
        }
}