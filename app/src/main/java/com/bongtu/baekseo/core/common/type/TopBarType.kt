package com.bongtu.baekseo.core.common.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

enum class TopBarType(
    val arrangement: Arrangement.Horizontal,
    val padding: PaddingValues,
) {
    TEXT_ONLY_START(
        arrangement = Arrangement.Start,
        padding = PaddingValues(18.dp),
    ),
    TEXT_ONLY_CENTER(
        arrangement = Arrangement.Center,
        padding = PaddingValues(20.dp),
    ),
    LEADING_ICON(
        arrangement = Arrangement.Start,
        padding = PaddingValues(vertical = 8.dp),
    ),
    TRAILING_ICON(
        arrangement = Arrangement.Start,
        padding = PaddingValues(start = 20.dp, top = 8.dp, bottom = 8.dp),
    ),
    BOTH_ICONS(
        arrangement = Arrangement.Start,
        padding = PaddingValues(vertical = 8.dp),
    );
}
