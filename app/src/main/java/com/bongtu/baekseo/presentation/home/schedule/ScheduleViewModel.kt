package com.bongtu.baekseo.presentation.home.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.home.schedule.ScheduleContract.ScheduleState
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
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

    fun fetchScheduleEvent(page: Int, category: String? = null) {
        viewModelScope.launch {
            eventRepository.getScheduleEvents(
                page = page,
                category = category,
            ).onSuccess { response ->
                updateScheduleUiState(
                    value = UiState.Success(response)
                )
            }.onFailure {
                updateScheduleUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }
    }

    private fun updateScheduleUiState(value: UiState<ImmutableList<ScheduleEvent>>) =
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
                    eventCategoryType = eventCategoryType,
                )
            }
            fetchScheduleEvent(page = 0, category = eventCategoryType.label)
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