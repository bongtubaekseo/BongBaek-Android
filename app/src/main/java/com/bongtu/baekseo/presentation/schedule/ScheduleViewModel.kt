package com.bongtu.baekseo.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleSideEffect
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val usernameDataStore: UsernameDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ScheduleSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _page = MutableStateFlow(0)

    private val _isLast = MutableStateFlow(false)

    fun fetchScheduleEvent() =
        viewModelScope.launch {
            val isFirstPage = _page.value == 0

            if (isFirstPage) updateScheduleUiState(UiState.Loading)

            eventRepository.getScheduleEvents(
                page = _page.value,
                category = uiState.value.eventCategoryType.label,
            ).onSuccess { response ->
                val newEvents = response.events
                val updatedList =
                    if (isFirstPage) {
                        newEvents
                    } else {
                        val existingEvents = uiState.value.scheduleList
                        existingEvents + newEvents
                    }

                _uiState.update { currentState ->
                    currentState.copy(
                        scheduleList = updatedList.toPersistentList(),
                        scheduleLoadState = when {
                            updatedList.isEmpty() -> UiState.Empty
                            else -> UiState.Success(Unit)
                        }
                    )
                }
                _isLast.value = response.isLast
                _page.value = response.currentPage
            }.onFailure {
                updateScheduleUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }

    private fun updateScheduleUiState(value: UiState<Unit>) =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    scheduleLoadState = value,
                )
            }
        }

    private fun updateScheduleList(value: ImmutableList<ScheduleEvent>) =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    scheduleList = value,
                )
            }
        }

    fun updateEventType(eventCategoryType: EventCategoryType) =
        viewModelScope.launch {
            _page.value = 0
            _isLast.value = false

            _uiState.update { currentState ->
                currentState.copy(
                    eventCategoryType = eventCategoryType,
                )
            }
            fetchScheduleEvent()
        }

    fun updatePage() =
        viewModelScope.launch {
            if (!_isLast.value) {
                _page.value += 1
                fetchScheduleEvent()
            }
        }

    fun clearPage() =
        viewModelScope.launch {
            _page.value = 0
            _isLast.value = false
        }

    fun getUsername() =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    name = usernameDataStore.getUsername()
                )
            }
        }

    fun navigateToHome() = viewModelScope.launch {
        _sideEffect.emit(ScheduleSideEffect.NavigateToHome)
    }

    fun navigateToDetail(eventId: String) = viewModelScope.launch {
        _sideEffect.emit(ScheduleSideEffect.NavigateToDetail(eventId))
    }

    fun navigateToEdit() = viewModelScope.launch {
        _sideEffect.emit(ScheduleSideEffect.NavigateToEdit)
    }
}