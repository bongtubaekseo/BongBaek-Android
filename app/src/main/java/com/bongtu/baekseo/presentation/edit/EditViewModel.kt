package com.bongtu.baekseo.presentation.edit

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.data.dto.event.FetchHomeEventsResponse.LocationInfo
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    // TODO: repository 추가 예정
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    fun updateHostName(newHostName: String) =
        _uiState.update { currentState ->
            currentState.copy(
                hostName = newHostName,
            )
        }

    fun updateHostNickname(newHostNickname: String) =
        _uiState.update { currentState ->
            currentState.copy(
                hostNickname = newHostNickname,
            )
        }

    fun updateEventCategory(newEventCategory: String) =
        _uiState.update { currentState ->
            currentState.copy(
                eventCategory = newEventCategory,
            )
        }

    fun updateRelationship(newRelationship: String) =
        _uiState.update { currentState ->
            currentState.copy(
                relationship = newRelationship,
            )
        }

    fun updateCost(newCost: String) =
        _uiState.update { currentState ->
            currentState.copy(
                cost = newCost,
            )
        }

    fun updateIsEventParticipated(newIsEventParticipated: Boolean) =
        _uiState.update { currentState ->
            currentState.copy(
                isEventParticipated = newIsEventParticipated,
            )
        }

    fun updateEventDate(newEventDate: String) =
        _uiState.update { currentState ->
            currentState.copy(
                eventDate = newEventDate,
            )
        }

    fun updateNote(newNote: String) =
        _uiState.update { currentState ->
            currentState.copy(
                note = newNote,
            )
        }

    fun updateLocationInfo(newLocationInfo: LocationInfo?) =
        _uiState.update { currentState ->
            currentState.copy(
                locationInfo = newLocationInfo,
            )
        }
}