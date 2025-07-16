package com.bongtu.baekseo.presentation.home.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.home.schedule.ScheduleContract.ScheduleState
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
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

    fun fetchScheduleEvent() {
        viewModelScope.launch {
            eventRepository.getScheduleEvents(
                page = uiState.value.page,
                category = uiState.value.eventCategoryType.label,
            ).onSuccess { response ->
                val newEvents = response.events
                val updatedList =
                    if (uiState.value.page == 0) {
                        newEvents
                    } else {
                        val existingEvents =
                            (uiState.value.scheduleLoadState as? UiState.Success)?.data?.events
                                ?: persistentListOf()
                        existingEvents + newEvents
                    }

                _uiState.update { currentState ->
                    currentState.copy(
                        scheduleLoadState = if (updatedList.isEmpty()) {
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
                updateScheduleUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }
    }

    private fun updateScheduleUiState(value: UiState<PageScheduleEvent>) =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    scheduleLoadState = value,
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
            fetchScheduleEvent()
        }

    fun updatePage() =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    page = uiState.value.page + 1,
                )
            }
            if (!uiState.value.isLast) fetchScheduleEvent()
        }

    fun getUsername() =
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    name = usernameDataStore.getUsername()
                )
            }
        }
}