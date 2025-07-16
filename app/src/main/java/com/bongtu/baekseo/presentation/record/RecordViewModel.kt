package com.bongtu.baekseo.presentation.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.data.dto.event.DeleteRecordRequest
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToAdd
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.record.RecordContract.RecordUiState
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RecordSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private fun deleteEvents() {
        viewModelScope.launch {
            eventRepository.deleteEvents(
                request = DeleteRecordRequest(uiState.value.selectedDeleteEventIds.toList()),
            ).onSuccess {
                updateSelectedDeleteEventIds(emptySet())
                updateDeleteModeCancel()
                _uiState.update { currentState ->
                    currentState.copy(
                        page = 0,
                        attendType = uiState.value.attendType,
                        eventCategoryType = uiState.value.eventCategoryType,
                    )
                }
                fetchRecordEvent()

            }.onFailure {
                updateRecordUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }
    }

    fun fetchRecordEvent() {
        viewModelScope.launch {
            eventRepository.getRecordEvents(
                page = uiState.value.page,
                attended = uiState.value.attendType.isAttended,
                category = uiState.value.eventCategoryType.label,
            ).onSuccess { response ->
                val newEvents = response.events
                val updatedList =
                    if (uiState.value.page == 0) {
                        newEvents
                    } else {
                        val existingEvents =
                            (uiState.value.recordLoadState as? UiState.Success)?.data?.events
                                ?: persistentListOf()
                        existingEvents + newEvents
                    }

                _uiState.update { currentState ->
                    currentState.copy(
                        recordLoadState = if (updatedList.isEmpty()) {
                            UiState.Empty
                        } else {
                            UiState.Success(
                                PageScheduleEvent(
                                    events = updatedList.toPersistentList(),
                                    currentPage = response.currentPage,
                                    isLast = response.isLast,
                                )
                            )
                        },
                        page = response.currentPage,
                        isLast = response.isLast,
                    )
                }
            }.onFailure {
                updateRecordUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }
    }

    fun fetchSelectedDeleteEventIds() {
        // TODO("선택된 삭제 처리 추가 예정")
        deleteEvents()
        updateDeleteModeCancel()
//        updateSelectedDeleteEventIds(emptySet())
    }

    private fun updateRecordUiState(value: UiState<PageScheduleEvent>) =
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

    fun updateNextPage() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    page = uiState.value.page + 1,
                )
            }
        }
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
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newSelected = if (currentState.selectedDeleteEventIds.contains(eventId)) {
                    currentState.selectedDeleteEventIds - eventId
                } else {
                    currentState.selectedDeleteEventIds + eventId
                }

                Timber.tag("하하하하하").d("선택된 ID 목록: $newSelected")

                currentState.copy(
                    selectedDeleteEventIds = newSelected
                )
            }
        }

    fun updateEventType(eventCategoryType: EventCategoryType) =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    page = 0,
                    eventCategoryType = eventCategoryType,
                )
            }
            fetchRecordEvent()
        }

    fun selectAttendType(newAttendType: AttendType) {

        _uiState.update { currentState ->
            currentState.copy(
                page = 0,
                attendType = newAttendType,
                eventCategoryType = EventCategoryType.ALL,
            )
        }
        fetchRecordEvent()
    }

    fun navigateToDetail(eventId: String) = viewModelScope.launch {
        _sideEffect.emit(NavigateToDetail(eventId))
    }

    fun navigateToAdd() = viewModelScope.launch {
        _sideEffect.emit(NavigateToAdd)
    }
}