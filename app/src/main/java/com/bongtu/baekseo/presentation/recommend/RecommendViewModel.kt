package com.bongtu.baekseo.presentation.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.data.model.event.EditEvent
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.data.repository.map.KakaoMapRepository
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.MainSideEffect.NavigateToLoading
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.ResultSideEffect.NavigateToFinal
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject
import kotlin.math.roundToInt

@OptIn(FlowPreview::class)
@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val usernameDataStore: UsernameDataStore,
    private val kakaoMapRepository: KakaoMapRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecommendUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _isManualSearch = MutableStateFlow(true)

    private val _sideEffect = MutableSharedFlow<RecommendSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        updateUsername()

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

    private fun updateUsername() = viewModelScope.launch {
        _uiState.update { currentState ->
            currentState.copy(username = usernameDataStore.getUsername())
        }
    }

    fun resetUiState() = _uiState.update {
        RecommendUiState(username = _uiState.value.username)
    }

    fun updateSearchTerm(searchTerm: String) {
        _isManualSearch.value = true
        _searchTerm.value = searchTerm
    }

    private fun updateLoadState(newLoadState: UiState<Unit>) = _uiState.update { currentState ->
        currentState.copy(loadState = newLoadState)
    }

    fun updatePageIndex(newIndex: Int) = _uiState.update {
        it.copy(pageIndex = newIndex)
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

    fun updateRelationType(newRelationType: RelationType) = _uiState.update {
        it.copy(relationType = newRelationType)
    }

    fun updateIsHighAccuracy(newIsHighAccuracy: Boolean) = _uiState.update {
        it.copy(isHighAccuracy = newIsHighAccuracy)
    }

    fun updateContactFrequency(newContactFrequency: Float) = _uiState.update {
        it.copy(contactFrequency = newContactFrequency)
    }

    fun updateMeetFrequency(newMeetFrequency: Float) = _uiState.update {
        it.copy(meetFrequency = newMeetFrequency)
    }

    fun updateEventType(newEventType: EventType) = _uiState.update {
        it.copy(eventType = newEventType)
    }

    fun updateEventDate(newEventDate: String) = _uiState.update {
        it.copy(eventDate = newEventDate)
    }

    fun updateIsEventParticipated(newIsEventParticipated: Boolean) = _uiState.update {
        it.copy(isEventParticipated = newIsEventParticipated)
    }

    fun updateEventLocation(newLocation: Place?) {
        _uiState.update {
            it.copy(selectedPlace = newLocation)
        }
        _isManualSearch.value = false
        _searchTerm.value = newLocation?.name.orEmpty()
    }

    fun updateButtonState(): Boolean {
        with(uiState.value) {
            return when (pageIndex) {
                1 -> name.isNotEmpty() && nickname.isNotEmpty() && relationType != null
                        && nameError.isEmpty() && nicknameError.isEmpty()

                2 -> eventType != null
                3 -> eventDate.isNotEmpty() && isEventParticipated != null
                else -> selectedPlace != null
            }
        }
    }

    fun fetchExpense() = viewModelScope.launch {
        with(uiState.value) {
            updateLoadState(UiState.Loading)

            eventRepository.postEventCost(
                event = Event(
                    eventType = requireNotNull(eventType).label,
                    relationType = requireNotNull(relationType).label,
                    cost = expense,
                    isEventParticipated = requireNotNull(isEventParticipated),
                    eventDate = eventDate,
                    note = "",
                ),
                location = Location(
                    location = selectedPlace?.name.orEmpty(),
                    address = selectedPlace?.address.orEmpty(),
                    latitude = selectedPlace?.latitude ?: 0.0,
                    longitude = selectedPlace?.longitude ?: 0.0,
                ),
                highAccuracy = HighAccuracy(
                    contactFrequency = if (isHighAccuracy) mapFrequencyToScale(contactFrequency) else DEFAULT_WEIGHT,
                    meetFrequency = if (isHighAccuracy) mapFrequencyToScale(meetFrequency) else DEFAULT_WEIGHT,
                )
            ).onSuccess { response ->
                _uiState.update {
                    it.copy(
                        expense = response.cost,
                        minExpense = response.min,
                        maxExpense = response.max,
                    )
                }
                updateLoadState(UiState.Success(Unit))

                Timber.d("fetchExpense: $response")
                _sideEffect.emit(NavigateToLoading)
            }.onFailure {
                // TODO: 실패 처리
                updateLoadState(UiState.Failure(ERROR_MESSAGE))

                Timber.d("fetchExpense: $it")
            }
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

    fun saveEventInformation() = viewModelScope.launch {
        with(uiState.value) {
            eventRepository.postEventInfo(
                host = Host(
                    name = name,
                    nickname = nickname,
                ),
                event = Event(
                    eventType = requireNotNull(eventType).label,
                    relationType = requireNotNull(relationType).label,
                    cost = expense,
                    isEventParticipated = requireNotNull(isEventParticipated),
                    eventDate = eventDate,
                    note = "",
                ),
                location = Location(
                    location = selectedPlace?.name.orEmpty(),
                    address = selectedPlace?.address.orEmpty(),
                    latitude = selectedPlace?.latitude ?: 0.0,
                    longitude = selectedPlace?.longitude ?: 0.0,
                ),
                highAccuracy = HighAccuracy(
                    contactFrequency = if (isHighAccuracy) mapFrequencyToScale(contactFrequency) else DEFAULT_WEIGHT,
                    meetFrequency = if (isHighAccuracy) mapFrequencyToScale(meetFrequency) else DEFAULT_WEIGHT,
                )
            )
        }.onSuccess { response ->
            Timber.d("saveEventInformation: $response")
            _sideEffect.emit(NavigateToFinal)
        }.onFailure {
            // TODO: 실패 처리
            Timber.d("saveEventInformation: $it")
        }
    }

    fun navigateToEdit() = viewModelScope.launch {
        val editEvent = with(uiState.value) {
            EditEvent(
                eventId = "",
                hostName = name,
                hostNickname = nickname,
                eventCategory = requireNotNull(eventType).label,
                relationship = requireNotNull(relationType).label,
                cost = expense,
                isEventParticipated = requireNotNull(isEventParticipated),
                eventDate = eventDate,
                note = "",
                location = selectedPlace?.name.orEmpty(),
                address = selectedPlace?.address.orEmpty(),
                latitude = selectedPlace?.latitude ?: 0.0,
                longitude = selectedPlace?.longitude ?: 0.0,
            )
        }
        _sideEffect.emit(RecommendSideEffect.ResultSideEffect.NavigateToEdit(editEvent))
    }

    companion object {
        private const val DEFAULT_WEIGHT = 3
        private const val DEBOUNCE_DELAY = 500L
        private const val ERROR_MESSAGE = "Failed API Connection"
        private fun mapFrequencyToScale(value: Float) = (value * 4 + 1).roundToInt().coerceIn(1, 5)
    }
}
