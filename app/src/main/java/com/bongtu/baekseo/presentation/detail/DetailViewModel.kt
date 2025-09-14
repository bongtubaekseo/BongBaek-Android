package com.bongtu.baekseo.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.DetailEvent
import com.bongtu.baekseo.data.model.event.EditEvent
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
    savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val eventId: String? = savedStateHandle.get<String>(EVENT_ID_KEY)

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<DetailSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchDetailEvent() = viewModelScope.launch {
        if (eventId != null) {
            eventRepository.getEventDetail(eventId)
                .onSuccess { response ->
                    updateDetailEvent(
                        value = response,
                    )
                    Timber.d("$response")
                }
                .onFailure {
                    // TODO: 실패 처리
                    Timber.d("fetchDetailEvent: $it")
                }
        }
    }

    private fun updateDetailEvent(value: DetailEvent) =
        _uiState.update { currentState ->
            currentState.copy(
                eventId = value.eventId,
                hostName = value.hostName,
                hostNickname = value.hostNickname,
                eventCategory = value.eventCategory,
                relationship = value.relationship,
                cost = value.cost,
                isEventParticipated = value.isEventParticipated,
                eventDate = value.eventDate,
                note = value.note,
                locationInfo = value.locationInfo,
            )
        }

    fun navigateToEdit() = viewModelScope.launch {
        val editEvent = with(uiState.value) {
            EditEvent(
                eventId = eventId,
                hostName = hostName,
                hostNickname = hostNickname,
                eventCategory = eventCategory,
                relationship = relationship,
                cost = cost,
                isEventParticipated = isEventParticipated,
                eventDate = eventDate,
                note = note ?: "",
                location = locationInfo?.location ?: "",
                address = locationInfo?.address ?: "",
                latitude = locationInfo?.latitude ?: 0.0,
                longitude = locationInfo?.longitude ?: 0.0,
            )
        }
        _sideEffect.emit(NavigateToEdit(editEvent))
    }

    fun removeDetailEvent(eventId: String) = viewModelScope.launch {
        updateLoadUiState(UiState.Loading)

        eventRepository.deleteEventInfo(eventId)
            .onSuccess { response ->
                Timber.tag("DetailViewModel").d("deleteEventInfo: $response")
                _sideEffect.emit(NavigateToRecord)
            }.onFailure {
                // TODO: 실패처리
                Timber.tag("DetailViewModel").e(it)
                updateLoadUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    private fun updateLoadUiState(newUiState: UiState<Unit>) =
        _uiState.update {
            it.copy(
                loadState = newUiState,
            )
        }

    companion object {
        private const val EVENT_ID_KEY = "eventId"
    }
}