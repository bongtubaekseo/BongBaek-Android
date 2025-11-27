package com.bongtu.baekseo.presentation.contents

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.data.model.content.Contents
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class ContentsContract {
    @Immutable
    data class ContentsUiState(
        val loadState: UiState<Unit> = UiState.Empty,
        val articles: ImmutableList<Contents> = persistentListOf(),
        val contentsDetail: ContentsDetail? = null,
        val selectedEvent: EventCategoryType = EventCategoryType.ALL,
    )

    sealed class ContentsSideEffect {
        data object NavigateToContentsDetail: ContentsSideEffect()
    }
}
