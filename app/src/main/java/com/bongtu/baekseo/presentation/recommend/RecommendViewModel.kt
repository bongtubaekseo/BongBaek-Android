package com.bongtu.baekseo.presentation.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.data.model.event.Event
import com.bongtu.baekseo.data.model.event.HighAccuracy
import com.bongtu.baekseo.data.model.event.Host
import com.bongtu.baekseo.data.model.event.Location
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.data.repository.map.KakaoMapRepository
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.MainSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.ResultSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

@OptIn(FlowPreview::class)
@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val kakaoMapRepository: KakaoMapRepository,
    private val eventRepository: EventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecommendUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RecommendSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            searchTerm.debounce(DEBOUNCE_DELAY).collect {
                searchPlaces()
            }
        }
    }

    fun updateSearchTerm(searchTerm: String) = _searchTerm.update { searchTerm }

    fun updatePageIndex(newIndex: Int) = _uiState.update {
        it.copy(pageIndex = newIndex)
    }

    fun updateName(newName: String) = _uiState.update {
        it.copy(name = newName)
    }

    fun updateNickname(newNickname: String) = _uiState.update {
        it.copy(nickname = newNickname)
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
        val formattedDate = "${newEventDate.substring(0, 2)}/${newEventDate.substring(2, 4)}/${newEventDate.substring(4)}"
        it.copy(eventDate = formattedDate)
    }

    fun updateIsEventParticipated(newIsEventParticipated: Boolean) = _uiState.update {
        it.copy(isEventParticipated = newIsEventParticipated)
    }

    fun updateEventLocation(newEventLocation: Pair<Double, Double>) = _uiState.update {
        it.copy(
            latitude = newEventLocation.first,
            longitude = newEventLocation.second,
        )
    }

    fun updateExpense(newExpense: Int) = _uiState.update {
        it.copy(expense = newExpense)
    }

    fun updateButtonState(): Boolean {
        with(_uiState.value) {
            return when (pageIndex) {
                1 -> name.isNotEmpty() && nickname.isNotEmpty() && relationType != null
                2 -> eventType != null
                3 -> eventDate.isNotEmpty() && isEventParticipated != null
                else -> latitude != 0.0 && longitude != 0.0
            }
        }
    }

    fun fetchExpense() = viewModelScope.launch {
        with(uiState.value) {
            eventRepository.postEventCost(
                event = Event(
                    eventType = eventType!!.label,
                    relationType = relationType!!.label,
                    cost = expense,
                    isEventParticipated = isEventParticipated!!,
                    eventDate = eventDate,
                    note = "",
                ),
                location = Location(
                    location = location,
                    address = address,
                    latitude = latitude,
                    longitude = longitude,
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
                Timber.d("fetchExpense: $response")
                _sideEffect.emit(MainSideEffect.NavigateToResult)
            }.onFailure {
                // TODO: 실패 처리
                Timber.d("fetchExpense: $it")
            }
        }
    }

    private fun searchPlaces() = viewModelScope.launch {
        kakaoMapRepository.searchPlaces(searchTerm.value).onSuccess {
            _uiState.update {
                it.copy(
                    searchResult = it.searchResult,
                )
            }
        }.onFailure {
            // TODO: 실패 처리
            Timber.d("searchPlaces: $it")
        }
    }

    fun saveEventInformation() = viewModelScope.launch {
        with(uiState.value) {
            // TODO: 반복되는 부분은 추후 리팩토링해서 묶을 수 있게끔 설정
            // TODO: null-assertion vs elvis 멘토링 이후에 리팩
            eventRepository.postEventInfo(
                host = Host(
                    name = name,
                    nickname = nickname,
                ),
                event = Event(
                    eventType = eventType!!.label,
                    relationType = relationType!!.label,
                    cost = expense,
                    isEventParticipated = isEventParticipated!!,
                    eventDate = eventDate.replace("/", "-"),
                    note = "",
                ),
                location = Location(
                    location = location,
                    address = address,
                    latitude = latitude,
                    longitude = longitude,
                ),
                highAccuracy = HighAccuracy(
                    contactFrequency = if (isHighAccuracy) mapFrequencyToScale(contactFrequency) else DEFAULT_WEIGHT,
                    meetFrequency = if (isHighAccuracy) mapFrequencyToScale(meetFrequency) else DEFAULT_WEIGHT,
                )
            )
        }.onSuccess { response ->
            Timber.d("saveEventInformation: $response")
            _sideEffect.emit(ResultSideEffect.NavigateToFinal)
        }.onFailure {
            // TODO: 실패 처리
            Timber.d("saveEventInformation: $it")
        }
    }

    companion object {
        private const val DEFAULT_WEIGHT = 3
        private const val DEBOUNCE_DELAY = 500L
        private fun mapFrequencyToScale(value: Float) = (value * 4 + 1).roundToInt().coerceIn(1, 5)
    }
}
