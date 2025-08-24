package com.bongtu.baekseo.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bongtu.baekseo.core.util.TextFieldValidator.validateCost
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.core.util.toFormattedDate
import com.bongtu.baekseo.core.util.toFormattedMonthDayYear
import com.bongtu.baekseo.data.model.event.EditEvent
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.data.repository.map.KakaoMapRepository
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import com.bongtu.baekseo.presentation.edit.navigation.Edit
import com.bongtu.baekseo.presentation.edit.navigation.EditNavType
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class EditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository,
    private val kakaoMapRepository: KakaoMapRepository,
) : ViewModel() {
    private val initialEvent: EditEvent? = runCatching {
        savedStateHandle.toRoute<Edit>(typeMap = EditNavType.TYPE_MAP).editEvent
    }.getOrNull()

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<EditContract.EditSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _isManualSearch = MutableStateFlow(true)

    private val _entryType = MutableStateFlow<EditEntryType?>(null)

    init {
        getEditEvent()

        viewModelScope.launch {
            combine(
                _searchTerm,
                _isManualSearch,
                transform = { term, isManual -> term to isManual }
            )
                .debounce(DEBOUNCE_DELAY)
                .distinctUntilChanged()
                .collect { (term, isManual) ->
                    if (isManual && term.isNotBlank()) {
                        searchPlaces()
                    }
                }
        }
    }

    fun updateEntryType(type: EditEntryType) = _entryType.update { type }

    fun getEditEvent() {
        initialEvent?.run {
            val previousDate = try {
                LocalDate.parse(eventDate)
            } catch (_: DateTimeParseException) {
                _uiState.value.previousDate
            }
            val place =
                if (location.isEmpty() && address.isEmpty() && latitude == 0.0 && longitude == 0.0) null
                else
                    Place(
                        id = "",
                        name = location,
                        address = address,
                        roadAddress = "",
                        latitude = latitude,
                        longitude = longitude
                    )

            _uiState.update {
                it.copy(
                    eventId = eventId,
                    name = hostName,
                    nickname = hostNickname,
                    eventCategory = eventCategory,
                    relationship = relationship,
                    cost = cost.toString(),
                    attendLabel = if (isEventParticipated) ATTENDED else ABSENT,
                    eventDate = eventDate.toFormattedMonthDayYear(),
                    previousDate = previousDate,
                    note = note,
                    selectedPlace = place,
                )
            }
        }
    }

    fun updateName(newName: String) = _uiState.update {
        it.copy(
            name = newName,
            nameError = validateName(newName),
        )
    }

    fun updateNickname(newNickname: String) = _uiState.update {
        it.copy(
            nickname = newNickname,
            nicknameError = validateName(newNickname),
        )
    }

    fun updateEventCategory(newEventCategory: String) = _uiState.update {
        it.copy(eventCategory = newEventCategory)
    }

    fun updateRelationship(newRelationship: String) = _uiState.update {
        it.copy(relationship = newRelationship)
    }

    fun updateCost(newCost: String) = _uiState.update {
        val digits = newCost.filter { it.isDigit() }
        val costText =
            if (digits.isEmpty()) "" else digits.toLong().coerceAtMost(99_999_999).toString()
        it.copy(
            cost = costText,
            costError = validateCost(costText)
        )
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
        it.copy(note = newNote)
    }

    fun updatePlace(newPlace: Place?) = _uiState.update {
        it.copy(selectedPlace = newPlace)
    }

    fun updateSearchTerm(newSearchTerm: String) = _searchTerm.update { newSearchTerm }

    fun clearSearchResult() = _uiState.update { it.copy(searchResult = persistentListOf()) }

    fun updateButtonState(): Boolean = with(uiState.value) {
        name.isNotBlank() &&
                nameError.isBlank() &&
                nickname.isNotBlank() &&
                nicknameError.isBlank() &&
                eventCategory.isNotBlank() &&
                relationship.isNotBlank() &&
                cost.isNotBlank() &&
                costError.isBlank() &&
                attendLabel.isNotBlank() &&
                eventDate.isNotBlank()
    }

    fun submitEventInformation(entryType: EditEntryType) {
        when (entryType) {
            EditEntryType.FROM_RECORD -> saveEventInformation()
            EditEntryType.FROM_SCHEDULE -> saveEventInformation()
            EditEntryType.FROM_DETAIL -> patchEventInformation()
            EditEntryType.FROM_RESULT -> saveEventInformation()
        }
    }

    private fun searchPlaces() = viewModelScope.launch {
        kakaoMapRepository.searchPlaces(searchTerm.value).onSuccess { response ->
            _uiState.update {
                it.copy(
                    searchResult = response,
                )
            }
            Timber.d("searchPlaces: $response")
        }.onFailure {
            // TODO: 실패 처리
            Timber.d("searchPlaces: $it")
        }
    }

    private fun patchEventInformation() =
        viewModelScope.launch {
            with(uiState.value) {
                eventRepository.putEventInfo(
                    eventId = eventId,
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
                        note = note,
                    ),
                    location = Location(
                        location = selectedPlace?.name.orEmpty(),
                        address = selectedPlace?.address.orEmpty(),
                        latitude = selectedPlace?.latitude ?: 0.0,
                        longitude = selectedPlace?.longitude ?: 0.0,
                    ),
                ).onSuccess { response ->
                    Timber.tag("patchEditEventInformation").d("response: $response")
                    navigateToComplete()
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
                    navigateToComplete()
                }.onFailure {
                    // TODO: 실패 처리
                    Timber.tag("saveEditEventInformation").d("Error: $it")
                }
            }
        }

    fun navigateToLocation() = viewModelScope.launch {
        _sideEffect.emit(EditMainSideEffect.NavigateToLocation)
    }

    fun navigateToComplete() = viewModelScope.launch {
        when (_entryType.value!!) {
            EditEntryType.FROM_RECORD -> _sideEffect.emit(EditMainSideEffect.NavigateToRecord)
            EditEntryType.FROM_SCHEDULE -> _sideEffect.emit(EditMainSideEffect.NavigateToSchedule)
            EditEntryType.FROM_DETAIL -> _sideEffect.emit(EditMainSideEffect.NavigateToDetail)
            EditEntryType.FROM_RESULT -> _sideEffect.emit(EditMainSideEffect.NavigateToFinal)
        }
    }

    companion object {
        private const val DEBOUNCE_DELAY = 500L
        private const val DEFAULT_WEIGHT = 3
        private const val ATTENDED = "참석"
        private const val ABSENT = "불참석"
    }
}