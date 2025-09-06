package com.bongtu.baekseo.core.common.type

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

enum class ScheduleCardType(
    val padding: PaddingValues,
) {
    HOME(
        padding = PaddingValues(
            horizontal = 20.dp,
            vertical = 16.dp,
        )
    ),
    SCHEDULE(
        padding = PaddingValues(
            horizontal = 20.dp,
            vertical = 16.dp,
        )
    ),
}
