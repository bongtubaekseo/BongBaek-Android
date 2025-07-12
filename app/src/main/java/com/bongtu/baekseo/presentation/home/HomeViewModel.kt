package com.bongtu.baekseo.presentation.home

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect
import com.bongtu.baekseo.presentation.home.HomeContract.HomeState
import com.bongtu.baekseo.presentation.home.model.HomeEvent
import com.bongtu.baekseo.presentation.home.model.HomeEventInfo
import com.bongtu.baekseo.presentation.home.model.HomeHostInfo
import com.bongtu.baekseo.presentation.home.model.HomeLocationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // TODO: Repository 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchHomeEvent() {
        // TODO: API 연동
        updateHomeUiState(
            value = UiState.Success(
                persistentListOf(
                    HomeEvent(
                        eventId = "1",
                        hostInfo = HomeHostInfo(
                            hostName = "공승준",
                            hostNickname = "초록승준",
                        ),
                        eventInfo = HomeEventInfo(
                            eventCategory = EventType.WEDDING,
                            relationship = RelationType.FRIEND,
                            cost = 10000,
                            eventDate = LocalDate.of(2025, 2, 11),
                            dDay = 11,
                        ),
                        locationInfo = HomeLocationInfo(
                            location = "강남구 테헤란로 강남 웨딩홀"
                        ),
                    ),
                    HomeEvent(
                        eventId = "2",
                        hostInfo = HomeHostInfo(
                            hostName = "김종명",
                            hostNickname = "봉준호",
                        ),
                        eventInfo = HomeEventInfo(
                            eventCategory = EventType.FIRST_BD,
                            relationship = RelationType.NEIGHBOR,
                            cost = 10000,
                            eventDate = LocalDate.of(2025, 2, 11),
                            dDay = 1,
                        ),
                        locationInfo = HomeLocationInfo(
                            location = "강남구 테헤란로 강남 웨딩홀"
                        ),
                    ),
                    HomeEvent(
                        eventId = "3",
                        hostInfo = HomeHostInfo(
                            hostName = "김헤정",
                            hostNickname = "메정",
                        ),
                        eventInfo = HomeEventInfo(
                            eventCategory = EventType.BIRTHDAY,
                            relationship = RelationType.ALUMNI,
                            cost = 10000,
                            eventDate = LocalDate.of(2025, 2, 11),
                        ),
                        locationInfo = HomeLocationInfo(
                            location = "강남구 테헤란로 강남 웨딩홀"
                        ),
                    ),
                )
            )
        )
    }

    private fun updateHomeUiState(value: UiState<ImmutableList<HomeEvent>>) {
        _uiState.update { currentState ->
            currentState.copy(
                homeLoadState = value,
            )
        }
    }
}