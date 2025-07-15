package com.bongtu.baekseo.presentation.edit

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.map.Place
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
}