package com.bongtu.baekseo.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.DetailEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailSideEffect
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailSideEffect.NavigateToEdit
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailSideEffect.NavigateToRecord
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchDetailEvent(eventId: String) = viewModelScope.launch {
        eventRepository.getEventDetail(eventId)
            .onSuccess { response ->
                updateDetailEvent(
                    value = UiState.Success(response)
                )
            }
            .onFailure {
                updateDetailEvent(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    private fun updateDetailEvent(value: UiState<DetailEvent>) =
        _uiState.update { currentState ->
            currentState.copy(
                loadState = value,
            )
        }

    fun navigateToEdit() = viewModelScope.launch {
        // TODO: Edit 에 caching 필요
        _sideEffect.emit(NavigateToEdit)
    }

    fun removeDetailEvent() = viewModelScope.launch {
        eventRepository.deleteEventInfo((uiState.value.loadState as UiState.Success).data.eventId)
            .onSuccess { response ->
                Timber.tag("DetailViewModel").d("deleteEventInfo: $response")
                _sideEffect.emit(NavigateToRecord)
            }.onFailure {
                Timber.tag("DetailViewModel").e(it)
            }
    }
}