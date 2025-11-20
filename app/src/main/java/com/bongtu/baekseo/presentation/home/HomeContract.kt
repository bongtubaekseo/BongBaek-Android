package com.bongtu.baekseo.presentation.home

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.content.HomeContent
import com.bongtu.baekseo.data.model.event.HomeEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class HomeContract {

    @Immutable
    data class HomeState(
        val homeLoadState: UiState<Unit> = UiState.Loading,
        val homeEventList: ImmutableList<HomeEvent> = persistentListOf(),
        val homeContentList: ImmutableList<HomeContent> = persistentListOf(),
        val name: String = "",
    )

    sealed class HomeSideEffect {
        data object NavigateToSchedule : HomeSideEffect()
        data object NavigateToEdit : HomeSideEffect()
        data object NavigateToRecommend : HomeSideEffect()
    }
}