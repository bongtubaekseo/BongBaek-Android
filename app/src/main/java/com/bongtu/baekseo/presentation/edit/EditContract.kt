package com.bongtu.baekseo.presentation.edit

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.map.Place
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

class EditContract {
    @Immutable
    data class EditUiState(
        val submitState: UiState<Unit> = UiState.Empty,
        val eventId: String = "",
        val name: String = "",
        val nameError: String = "",
        val nickname: String = "",
        val nicknameError: String = "",
        val eventCategory: String = "",
        val relationship: String = "",
        val cost: String = "",
        val costError: String = "",
        val attendLabel: String = "",
        val eventDate: String = "",
        val previousDate: LocalDate = LocalDate.now().minusDays(1),
        val note: String = "",
        val searchResult: ImmutableList<Place> = persistentListOf(),
        val selectedPlace: Place? = null,
    )

    sealed interface EditSideEffect {

        sealed class EditMainSideEffect : EditSideEffect {
            data object NavigateToRecord : EditMainSideEffect()
            data object NavigateToDetail: EditMainSideEffect()

            data object NavigateToFinal : EditMainSideEffect()
            data object NavigateToSchedule : EditMainSideEffect()
            data object NavigateToLocation : EditMainSideEffect()
        }

        sealed class EditLocationSideEffect : EditSideEffect {
            data object NavigateToEditMain : EditLocationSideEffect()
        }
    }
}