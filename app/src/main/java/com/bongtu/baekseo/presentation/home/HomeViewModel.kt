package com.bongtu.baekseo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect
import com.bongtu.baekseo.presentation.home.HomeContract.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val usernameDataStore: UsernameDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchHomeEvent() {
        viewModelScope.launch {
            eventRepository.fetchHomeEvents()
                .onSuccess { response ->
                    updateHomeUiState(
                        value = UiState.Success(response)
                    )
                }
                .onFailure {
                    updateHomeUiState(UiState.Failure(it.message ?: "Unknown Error"))
                }
        }
    }

    private fun updateHomeUiState(value: UiState<ImmutableList<HomeEvent>>) {
        _uiState.update { currentState ->
            currentState.copy(
                homeLoadState = value,
            )
        }
    }

    fun getUsername() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    name = usernameDataStore.getUsername()
                )
            }
        }
    }
}