package com.bongtu.baekseo.presentation.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.data.model.event.DeleteEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToAdd
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.record.RecordContract.RecordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RecordSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _page = MutableStateFlow(0)

    private val _isLast = MutableStateFlow(false)

    fun fetchRecordEvent(requestedPage: Int? = null) =
        viewModelScope.launch {
            val page = requestedPage ?: _page.value
            val isFirstPage = page == 0

            if (isFirstPage) updateRecordUiState(UiState.Loading)

            eventRepository.getRecordEvents(
                page = page,
                year = uiState.value.selectedDate.year,
                month = uiState.value.selectedDate.monthValue,
                attended = uiState.value.attendType.isAttended,
                category = uiState.value.eventCategoryType.paramLabel,
            ).onSuccess { response ->
                val newEvents = response.events
                val updatedList =
                    if (isFirstPage) newEvents
                    else uiState.value.scheduleList + newEvents

                _uiState.update { current ->
                    current.copy(
                        scheduleList = updatedList.toPersistentList(),
                        recordLoadState =
                            if (updatedList.isEmpty()) UiState.Empty
                            else UiState.Success(Unit),
                    )
                }

                _isLast.value = response.isLast
                _page.value = response.currentPage
            }.onFailure {
                updateRecordUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }

    private fun deleteEvents() =
        viewModelScope.launch {
            eventRepository.deleteEvents(
                request = DeleteEvent(uiState.value.selectedDeleteEventIds),
            ).onSuccess {
                updateSelectedDeleteEventIds(emptySet())
                clearPage()
                fetchRecordEvent(requestedPage = 0)
            }.onFailure {
                updateRecordUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }

    fun fetchSelectedDeleteEventIds() {
        deleteEvents()
        updateDeleteModeCancel()
    }

    private fun clearPage() {
        _page.value = 0
        _isLast.value = false
    }

    fun selectEventType(eventCategoryType: EventCategoryType) {
        clearPage()

        _uiState.update { currentState ->
            currentState.copy(
                eventCategoryType = eventCategoryType,
                scheduleList = persistentListOf(),
            )
        }
        fetchRecordEvent()
    }

    fun selectAttendType(newAttendType: AttendType) {
        clearPage()

        _uiState.update { currentState ->
            currentState.copy(
                attendType = newAttendType,
                eventCategoryType = EventCategoryType.ALL,
            )
        }
        fetchRecordEvent()
    }

    fun initRecordUiState() {
        clearPage()
        _uiState.update {
            it.copy(
                attendType = AttendType.ATTEND,
                eventCategoryType = EventCategoryType.ALL,
                scheduleList = persistentListOf(),
                isDeleteMode = false,
                selectedDeleteEventIds = emptySet(),
                recordLoadState = UiState.Loading,
            )
        }
    }

    private fun updateRecordUiState(value: UiState<Unit>) =
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
        if (!_isLast.value) {
            val next = _page.value + 1
            fetchRecordEvent(requestedPage = next)
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
        _uiState.update { currentState ->
            val newSelected = if (currentState.selectedDeleteEventIds.contains(eventId)) {
                currentState.selectedDeleteEventIds - eventId
            } else {
                currentState.selectedDeleteEventIds + eventId
            }

            currentState.copy(
                selectedDeleteEventIds = newSelected
            )
        }

    fun navigateToDetail(eventId: String) = viewModelScope.launch {
        _sideEffect.emit(NavigateToDetail(eventId))
    }

    fun navigateToAdd() = viewModelScope.launch {
        _sideEffect.emit(NavigateToAdd)
    }
}
