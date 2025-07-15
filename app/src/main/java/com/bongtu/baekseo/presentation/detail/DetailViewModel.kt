package com.bongtu.baekseo.presentation.detail

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.event.DetailEvent
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    // TODO: Repository 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchDetailEvent() {
        updateDetailEvent(
            value = UiState.Success(
                DetailEvent(
                    eventId = "1",
                    hostName = "홍길동",
                    hostNickname = "홍길동",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    isEventParticipated = true,
                    eventDate = "2025-07-05",
                    note = null,
                    locationInfo = null,
                )
            )
        )
    }

    private fun updateDetailEvent(value: UiState<DetailEvent>) =
        _uiState.update { currentState ->
            currentState.copy(
                loadState = value,
            )
        }
}