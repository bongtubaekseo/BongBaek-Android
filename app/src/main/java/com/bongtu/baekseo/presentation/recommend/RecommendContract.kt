package com.bongtu.baekseo.presentation.recommend

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType

class RecommendContract {
    @Immutable
    data class RecommendUiState(
        val loadState: UiState<Nothing> = UiState.Empty,
        val pageIndex: Int = 1,
        val name: String = "",
        val nickname: String = "",
        val relationType: RelationType? = null,
        val isHighAccuracy: Boolean = false,
        val contactFrequency: Float = 0f,
        val meetFrequency: Float = 0f,
        val eventType: EventType? = null,
        val eventDate: String = "",
        val isEventParticipated: Boolean? = null,
        val latitude: Double = 37.5665,
        val longitude: Double = 126.9780,
        val expense: Int = 0,
    )

    sealed interface RecommendSideEffect {
        sealed class MainSideEffect : RecommendSideEffect {
            data object NavigateToResult : MainSideEffect()
        }

        sealed class ResultSideEffect : RecommendSideEffect {
            data object NavigateToFinal : ResultSideEffect()
        }
    }
}
