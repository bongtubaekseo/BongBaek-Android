package com.bongtu.baekseo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.content.Contents
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.data.repository.content.ContentsRepository
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.domain.usecase.config.GetUpdateFlagUseCase
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToContentsDetail
import com.bongtu.baekseo.presentation.home.HomeContract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val contentsRepository: ContentsRepository,
    private val getUpdateFlagUseCase: GetUpdateFlagUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val isUpdateDialogVisible = getUpdateFlagUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    fun fetchHomeEvent() = viewModelScope.launch {
        eventRepository.getHomeEvents()
            .onSuccess { response ->
                updateHomeEvent(
                    value = response,
                )
            }
            .onFailure {
                updateHomeUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    fun fetchHomeContent() = viewModelScope.launch {
        contentsRepository.getHomeContents()
            .onSuccess { response ->
                updateHomeContents(
                    value = response,
                )
            }
            .onFailure {
                updateHomeUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    fun fetchContentsDetail(contentId: String) = viewModelScope.launch {
        contentsRepository.getContentsDetail(contentId)
            .onSuccess {
                _uiState.update { currentState ->
                    currentState.copy(contentsDetail = it)
                }
                _sideEffect.emit(NavigateToContentsDetail(it))
            }.onFailure {
                updateHomeUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    private fun updateHomeEvent(value: ImmutableList<HomeEvent>) =
        _uiState.update { currentState ->
            currentState.copy(
                homeEventList = value,
            )
        }

    private fun updateHomeContents(value: ImmutableList<Contents>) =
        _uiState.update { currentState ->
            currentState.copy(
                contentList = value,
            )
        }

    private fun updateHomeUiState(value: UiState<Unit>) =
        _uiState.update { currentState ->
            currentState.copy(
                homeLoadState = value,
            )
        }
}
