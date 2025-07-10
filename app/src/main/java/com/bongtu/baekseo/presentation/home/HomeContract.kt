package com.bongtu.baekseo.presentation.home

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.presentation.home.model.HomeEvent
import kotlinx.collections.immutable.ImmutableList

class HomeContract {

    @Immutable
    data class HomeState(
        val homeLoadState: UiState<ImmutableList<HomeEvent>> = UiState.Loading,
    )
}