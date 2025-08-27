package com.bongtu.baekseo.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _page = MutableStateFlow(0)

    private val _isLast = MutableStateFlow(false)

    init {
        getUsername()
    }

    fun fetchScheduleEvent(requestedPage: Int? = null) =
        viewModelScope.launch {
            val page = requestedPage ?: _page.value
            val isFirstPage = page == 0

            if (isFirstPage) updateScheduleUiState(UiState.Loading)

            eventRepository.getScheduleEvents(
                page = page,
                category = uiState.value.eventCategoryType.label,
            ).onSuccess { response ->
                val newEvents = response.events
                val updatedList =
                    if (isFirstPage) newEvents
                    else uiState.value.scheduleList + newEvents

                _uiState.update { current ->
                    current.copy(
                        scheduleList = updatedList.toPersistentList(),
                        scheduleLoadState =
                            if (updatedList.isEmpty()) UiState.Empty
                            else UiState.Success(Unit),
                    )
                }

                _isLast.value = response.isLast
                _page.value = response.currentPage
            }.onFailure {
                updateScheduleUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }

    fun selectEventType(eventCategoryType: EventCategoryType) {
        clearPage()

        _uiState.update { currentState ->
            currentState.copy(
                eventCategoryType = eventCategoryType,
                scheduleList = persistentListOf(),
            )
        }
        fetchScheduleEvent()
    }

    private fun updateScheduleUiState(value: UiState<Unit>) =
        _uiState.update { currentState ->
            currentState.copy(
                scheduleLoadState = value,
            )
        }

    fun updatePage() {
        if (!_isLast.value) {
            val next = _page.value + 1
            fetchScheduleEvent(requestedPage = next)
        }
    }

    fun clearPage() {
        _page.value = 0
        _isLast.value = false
    }

    private fun getUsername() = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(
                name = usernameDataStore.getUsername()
            )
        }
    }
}