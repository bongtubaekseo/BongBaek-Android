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
        data object NavigateToRecord : EditSideEffect
        data object NavigateToDetail : EditSideEffect
        data object NavigateToFinal : EditSideEffect
        data object NavigateToLocation : EditSideEffect
    }
}
