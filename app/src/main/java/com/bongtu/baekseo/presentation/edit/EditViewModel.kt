package com.bongtu.baekseo.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.designsystem.component.textfield.TextFieldValidateResult
import com.bongtu.baekseo.core.util.toFormattedDate
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditLocationSideEffect
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
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
class EditViewModel @Inject constructor(
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<EditContract.EditSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _nameValidate =
        MutableStateFlow<TextFieldValidateResult>(TextFieldValidateResult.Default)
    val nameValidate = _nameValidate.asStateFlow()

    private val _nicknameValidate =
        MutableStateFlow<TextFieldValidateResult>(TextFieldValidateResult.Default)
    val nickNameValidate = _nicknameValidate.asStateFlow()

    private val _costValidate =
        MutableStateFlow<TextFieldValidateResult>(TextFieldValidateResult.Default)
    val costValidate = _costValidate.asStateFlow()

    fun updateName(newName: String) {
        _uiState.update { it.copy(name = newName) }
        _nameValidate.update { TextFieldValidateResult.validate(newName) }
    }

    fun updateNickname(newNickname: String) {
        _uiState.update { it.copy(nickname = newNickname) }
        _nicknameValidate.update { TextFieldValidateResult.validate(newNickname) }
    }

    fun updateEventCategory(newEventCategory: String) = _uiState.update {
        it.copy(eventCategory = newEventCategory)
    }

    fun updateRelationship(newRelationship: String) = _uiState.update {
        it.copy(relationship = newRelationship)
    }

    fun updateCost(newCost: String) {
        _uiState.update { it.copy(cost = newCost) }
        _costValidate.update { TextFieldValidateResult.validateCost(newCost) }
    }

    fun updateAttendLabel(newAttendLabel: String) = _uiState.update {
        it.copy(
            attendLabel = newAttendLabel,
        )
    }

    fun updateEventDate(newEventDate: String) = _uiState.update {
        it.copy(
            eventDate = newEventDate,
        )
    }

    fun updateNote(newNote: String) = _uiState.update {
        it.copy(note = newNote,)
    }

    fun updatePlace(newPlace: Place?) = _uiState.update {
        it.copy(selectedPlace = newPlace)
    }

    fun updateSearchTerm(newSearchTerm: String) = _searchTerm.update { newSearchTerm }

    fun updateButtonState(): Boolean = with(uiState.value) {
        name.isNotBlank() && nameValidate.value == TextFieldValidateResult.Default &&
                nickname.isNotBlank() && nickNameValidate.value == TextFieldValidateResult.Default &&
                eventCategory.isNotBlank() &&
                relationship.isNotBlank() &&
                cost.isNotBlank() && costValidate.value == TextFieldValidateResult.Default &&
                attendLabel.isNotBlank() &&
                eventDate.isNotBlank()
    }

    fun submitEventInformation(entryType: EditEntryType) {
        when (entryType) {
            EditEntryType.FROM_RECORD -> saveEventInformation()
            EditEntryType.FROM_SCHEDULE -> patchEventInformation()
            EditEntryType.FROM_DETAIL -> patchEventInformation()
            EditEntryType.FROM_RESULT -> saveEventInformation()
        }
    }

    private fun patchEventInformation() =
        viewModelScope.launch {
            with(uiState.value) {
                eventRepository.putEventInfo(
                    eventId = "",                   // TODO: Caching eventId
                    host = Host(
                        name = name,
                        nickname = nickname,
                    ),
                    event = Event(
                        eventType = eventCategory,
                        relationType = relationship,
                        cost = cost.toInt(),
                        isEventParticipated = attendLabel == ATTENDED,
                        eventDate = eventDate.toFormattedDate(),
                        note = "",
                    ),
                    location = Location(
                        location = selectedPlace?.name.orEmpty(),
                        address = selectedPlace?.address.orEmpty(),
                        latitude = selectedPlace?.latitude ?: 0.0,
                        longitude = selectedPlace?.longitude ?: 0.0,
                    ),
                ).onSuccess { response ->
                    Timber.tag("patchEditEventInformation").d("response: $response")
                    _sideEffect.emit(EditMainSideEffect.NavigateToComplete)
                }.onFailure {
                    // TODO: 실패 처리
                    Timber.tag("patchEditEventInformation").d("Error: $it")
                }
            }
        }

    private fun saveEventInformation() =
        viewModelScope.launch {
            with(uiState.value) {
                eventRepository.postEventInfo(
                    host = Host(
                        name = name,
                        nickname = nickname,
                    ),
                    event = Event(
                        eventType = eventCategory,
                        relationType = relationship,
                        cost = cost.toInt(),
                        isEventParticipated = attendLabel == ATTENDED,
                        eventDate = eventDate.toFormattedDate(),
                        note = "",
                    ),
                    location = Location(
                        location = selectedPlace?.name.orEmpty(),
                        address = selectedPlace?.address.orEmpty(),
                        latitude = selectedPlace?.latitude ?: 0.0,
                        longitude = selectedPlace?.longitude ?: 0.0,
                    ),
                    highAccuracy = HighAccuracy(
                        contactFrequency = DEFAULT_WEIGHT,
                        meetFrequency = DEFAULT_WEIGHT,
                    ),
                ).onSuccess { response ->
                    Timber.tag("saveEditEventInformation").d("response: $response")
                    _sideEffect.emit(EditMainSideEffect.NavigateToComplete)
                }.onFailure {
                    // TODO: 실패 처리
                    Timber.tag("saveEditEventInformation").d("Error: $it")
                }
            }
        }

    fun navigateToLocation() = viewModelScope.launch {
        _sideEffect.emit(EditMainSideEffect.NavigateToLocation)
    }

    fun navigateToEditMain() = viewModelScope.launch {
        _sideEffect.emit(EditLocationSideEffect.NavigateToEditMain)
    }

    companion object {
        private const val DEFAULT_WEIGHT = 3
        private const val ATTENDED = "참석"
    }
}