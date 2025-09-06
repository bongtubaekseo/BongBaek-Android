package com.bongtu.baekseo.core.designsystem.component.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.designsystem.component.chip.BongBaekFilterChip
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun BongBaekCategoryBar(
    selectedCategory: EventCategoryType,
    onCategoryClick: (EventCategoryType) -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(
            items = EventCategoryType.entries,
            key = { _, type -> type.ordinal },
        ) { index, type ->
            BongBaekFilterChip(
                eventLabel = type.label,
                isSelected = type == selectedCategory,
                onClick = {
                    if (isEnabled && selectedCategory != type) onCategoryClick(type)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BongBaekCategoryBarPreview() {
    BongBaekTheme {
        BongBaekCategoryBar(
            selectedCategory = EventCategoryType.WEDDING,
            onCategoryClick = {},
            isEnabled = true,
        )
    }
}