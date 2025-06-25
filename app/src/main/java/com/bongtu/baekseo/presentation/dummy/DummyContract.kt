package com.bongtu.baekseo.presentation.dummy

import androidx.compose.runtime.Immutable
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.DummyUser

class DummyContract {
    @Immutable
    data class DummyState(
        val dummyUsersLoadState: UiState<List<DummyUser>> = UiState.Empty,
    )
}
