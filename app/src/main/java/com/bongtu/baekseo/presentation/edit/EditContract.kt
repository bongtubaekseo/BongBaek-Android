package com.bongtu.baekseo.presentation.edit

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.map.Place
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditContract {
    @Immutable
    data class EditUiState(
        val submitState: UiState<Unit> = UiState.Empty,
        val name: String = "",
        val nickname: String = "",
        val eventCategory: String = "",
        val relationship: String = "",
        val cost: String = "",
        val attendLabel: String = "",
        val eventDate: String = "",
        val note: String = "",
        val searchResult: ImmutableList<Place> = persistentListOf(),
        val selectedPlace: Place? = null,
    )

    sealed interface EditSideEffect {

        sealed class EditMainSideEffect : EditSideEffect {
            data object NavigateToComplete : EditMainSideEffect()
            data object NavigateToLocation : EditMainSideEffect()
        }

        sealed class EditLocationSideEffect : EditSideEffect {
            data object NavigateToEditMain : EditLocationSideEffect()
        }
    }
}