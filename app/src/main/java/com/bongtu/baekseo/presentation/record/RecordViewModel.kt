package com.bongtu.baekseo.presentation.record

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.Event
import com.bongtu.baekseo.data.model.EventInfo
import com.bongtu.baekseo.data.model.HostInfo
import com.bongtu.baekseo.data.repository.DummyRepository
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordState())
    val uiState = _uiState.asStateFlow()

    fun fetchRecordEvent() {
        TODO("서버 통신 연결")
        updateRecordUiState(
            value = UiState.Success(
                listOf(
                    Event(
                        eventId = 1,
                        host = HostInfo(name = "username", nickname = "nickname"),
                        event = EventInfo(
                            category = "경조사 유형",
                            relationship = "관계",
                            amount = 10000,
                            isAttend = true,
                            date = LocalDate.now(),
                        ),
                    ),
                    Event(
                        eventId = 1,
                        host = HostInfo(name = "username", nickname = "nickname"),
                        event = EventInfo(
                            category = "경조사 유형",
                            relationship = "관계",
                            amount = 10000,
                            isAttend = true,
                            date = LocalDate.now(),
                        ),
                    ),
                    Event(
                        eventId = 1,
                        host = HostInfo(name = "username", nickname = "nickname"),
                        event = EventInfo(
                            category = "경조사 유형",
                            relationship = "관계",
                            amount = 10000,
                            isAttend = true,
                            date = LocalDate.now(),
                        ),
                    ),
                    Event(
                        eventId = 1,
                        host = HostInfo(name = "username", nickname = "nickname"),
                        event = EventInfo(
                            category = "경조사 유형",
                            relationship = "관계",
                            amount = 10000,
                            isAttend = true,
                            date = LocalDate.now(),
                        ),
                    ),
                )
            )
        )
    }

    private fun updateRecordUiState(value: UiState<List<Event>>) {
        _uiState.update { currentState ->
            currentState.copy(
                recordLoadState = value,
            )
        }
    }
}