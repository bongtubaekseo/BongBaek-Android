package com.bongtu.baekseo.presentation.recommend

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.data.model.map.Place
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class RecommendContract {
    @Immutable
    data class RecommendUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val pageIndex: Int = 1,
        val username: String = "",
        val name: String = "",
        val nameError: String = "",
        val nickname: String = "",
        val nicknameError: String = "",
        val relationType: RelationType? = null,
        val isHighAccuracy: Boolean = false,
        val contactFrequency: Float = 0f,
        val meetFrequency: Float = 0f,
        val eventType: EventType? = null,
        val eventDate: String = "",
        val isEventParticipated: Boolean? = null,
        val searchResult: ImmutableList<Place> = persistentListOf(),
        val selectedPlace: Place? = null,
        val expense: Int = 0,
        val minExpense: Int = 0,
        val maxExpense: Int = 0,
    )

    sealed interface RecommendSideEffect {
        sealed class MainSideEffect : RecommendSideEffect {
            data object NavigateToLoading : MainSideEffect()
        }

        sealed class ResultSideEffect : RecommendSideEffect {
            data object NavigateToFinal : ResultSideEffect()
        }
    }
}
