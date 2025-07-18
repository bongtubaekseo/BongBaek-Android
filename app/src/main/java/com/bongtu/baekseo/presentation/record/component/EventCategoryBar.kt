package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.component.chip.BongBaekFilterChip
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

@Composable
fun EventCategoryBar(
    selectedCategory: EventCategoryType,
    onCategoryClick: (EventCategoryType) -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        itemsIndexed(EventCategoryType.entries) { index, type ->

            val isSelected = type == selectedCategory

            if (index == 0) {
                Spacer(modifier = Modifier.width(20.dp))
            }

            BongBaekFilterChip(
                eventLabel = type.label,
                isSelected = isSelected,
                onClick = {
                    if (isEnabled && selectedCategory != type) onCategoryClick(type)
                },
            )

            if (index == EventCategoryType.entries.lastIndex) {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EventCategoryBarPreview() {
    BongBaekTheme {
        EventCategoryBar(
            selectedCategory = EventCategoryType.WEDDING,
            onCategoryClick = {},
            isEnabled = true,
        )
    }
}