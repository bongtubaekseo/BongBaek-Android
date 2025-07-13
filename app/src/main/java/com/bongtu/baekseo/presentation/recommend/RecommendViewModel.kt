package com.bongtu.baekseo.presentation.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.MainSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.ResultSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    // TODO: Repository 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecommendUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<RecommendSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

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
        it.copy(eventDate = newEventDate)
    }

    fun updateIsEventParticipated(newIsEventParticipated: Boolean) = _uiState.update {
        it.copy(isEventParticipated = newIsEventParticipated)
    }

    fun updateEventLocation(newEventLocation: Pair<Double, Double>) = _uiState.update {
        it.copy(
            isLocationSelected = true,
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
                else -> isLocationSelected
            }
        }
    }

    fun fetchExpense() = viewModelScope.launch {
        // TODO: Repository 연결(경조사비 추천 GET 요청)
        _sideEffect.emit(MainSideEffect.NavigateToResult)
    }

    fun saveEventInformation() = viewModelScope.launch {
        // TODO: Repository 연결(경조사 정보 POST 요청)
        _sideEffect.emit(ResultSideEffect.NavigateToFinal)
    }
}
