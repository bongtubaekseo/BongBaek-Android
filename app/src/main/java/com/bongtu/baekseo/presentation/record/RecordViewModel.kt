package com.bongtu.baekseo.presentation.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.data.repository.DummyRepository
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect
import com.bongtu.baekseo.presentation.record.RecordContract.RecordUiState
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RecordSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _page = MutableStateFlow(0)  // TODO: 무한 스크롤 페이지 state

    fun fetchRecordEvent() {
        // TODO: categoryType 이 ALL 이면 쿼리 스트링 x
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
        updateSelectedDeleteEventIds(emptySet())
    }

    private fun updateRecordUiState(value: UiState<List<RecordEvent>>) =
        _uiState.update { currentState ->
            currentState.copy(
                recordLoadState = value,
            )
        }

    private fun updateSelectedDeleteEventIds(newSelectedDeleteEventIds: Set<String>) =
        _uiState.update { currentState ->
            currentState.copy(
                selectedDeleteEventIds = newSelectedDeleteEventIds,
            )
        }

    private fun updateAttendType(newAttendType: AttendType) =
        _uiState.update { currentState ->
            currentState.copy(
                attendType = newAttendType,
            )
        }

    private fun updateEventType(newEventCategoryType: EventCategoryType) =
        _uiState.update { currentState ->
            currentState.copy(
                eventCategoryType = newEventCategoryType,
            )
        }

    private fun updatePage(newPage: Int) {
        _page.value = newPage
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

    fun selectEventCategory(newCategoryType: EventCategoryType) {
        updateEventType(newCategoryType)
        updatePage(0)
        fetchRecordEvent()
    }

    fun selectAttendType(newAttendType: AttendType) {
        updateAttendType(newAttendType)
        updateEventType(EventCategoryType.ALL)
        updatePage(0)
        fetchRecordEvent()
    }

    fun navigateToDetail(eventId: String) = viewModelScope.launch {
        _sideEffect.emit(RecordSideEffect.NavigateToDetail(eventId))
    }

    fun navigateToAdd() = viewModelScope.launch {
        _sideEffect.emit(RecordSideEffect.NavigateToAdd)
    }
}