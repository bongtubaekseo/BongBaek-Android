package com.bongtu.baekseo.core.designsystem.component.dropdownmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * 공통 드롭 다운 메뉴
 * 보여지는 아이템 개수를 지정하여 설정 가능한 드롭다운
 * @param expanded 드롭 다운 메뉴가 열려있는지 여부
 * @param items 드롭 다운 메뉴에 표시될 아이템 리스트
 * @param maxItemSize 아이템의 최대 개수(보여지는 아이템의 개수)
 * @param selectedItem 선택된 아이템
 * @param onDismissRequest 드롭 다운 메뉴가 닫힐 때 호출되는 콜백
 * @param onItemSelect 아이템이 선택될 때 호출되는 콜백
 */
@Composable
fun <T> BongBaekDropdownMenu(
    expanded: Boolean,
    items: ImmutableList<T>,
    selectedItem: T?,
    onDismissRequest: () -> Unit,
    onItemSelect: (T) -> Unit,
    label: (T) -> String,
    modifier: Modifier = Modifier,
    maxItemSize: Int = 3,
    offset: DpOffset = DpOffset(0.dp, 14.dp),
    isFocusable: Boolean = false,
) {
    val bongBaekColors = BongBaekTheme.colors
    var itemHeightDp by remember { mutableStateOf(0.dp) }

    DropdownMenu(
        expanded = expanded,
        offset = offset,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(
            focusable = isFocusable,
        ),
        shape = RoundedCornerShape(10.dp),
        containerColor = bongBaekColors.gray750,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 4.dp,
            )
            .heightIn(
                max =
                    if (itemHeightDp > 0.dp) (itemHeightDp + 8.dp) * maxItemSize
                    else Dp.Unspecified,
            ),
    ) {
        items.forEachIndexed { index, item ->
            DropDownMenuItem<T>(
                item = item,
                index = index,
                isSelected = item == selectedItem,
                onFirstItemMeasured = { itemHeightDp = it },
                onItemSelected = {
                    onItemSelect(it)
                    onDismissRequest()
                },
                label = label,
            )
        }
    }
}

@Composable
private fun <T> DropDownMenuItem(
    item: T,
    index: Int,
    isSelected: Boolean?,
    onItemSelected: (T) -> Unit,
    onFirstItemMeasured: (Dp) -> Unit,
    label: (T) -> String,
) {
    val bongBaekColors = BongBaekTheme.colors
    val bongBaekTypography = BongBaekTheme.typography
    val density = LocalDensity.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val (backgroundColor, textColor) = remember(isSelected, isPressed) {
        when {
            isPressed -> bongBaekColors.primaryBackground to bongBaekColors.primaryNormal
            isSelected == true -> bongBaekColors.primaryBackground to bongBaekColors.primaryNormal
            else -> bongBaekColors.transparent to bongBaekColors.white
        }
    }
    val textStyle = remember(isSelected, isPressed) {
        when {
            isPressed -> bongBaekTypography.body1Medium16
            isSelected == true -> bongBaekTypography.body1Medium16
            else -> bongBaekTypography.body2Regular16
        }
    }

    Box(
        modifier = Modifier
            .onGloballyPositioned {
                if (index == 0) {
                    onFirstItemMeasured(with(density) { it.size.height.toDp() })
                }
            }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp),
            )
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onItemSelected(item)
            },
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = label(item),
            color = textColor,
            style = textStyle,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BongBaekDropdownMenuPreview() {
    BongBaekTheme {
        val dummyItems = remember {
            persistentListOf("강남 웨딩홀", "강남 어쩌구저쩌구", "강남 이러쿵저쩌쿵", "강남스타일", "강남 알베르")
        }
        var expanded by remember { mutableStateOf(true) }
        var selectedItem by remember { mutableStateOf("강남 웨딩홀") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            BongBaekDropdownMenu<String>(
                expanded = expanded,
                items = dummyItems,
                maxItemSize = 3,
                selectedItem = selectedItem,
                onDismissRequest = { expanded = false },
                onItemSelect = { selectedItem = it },
                label = { it },
                modifier = Modifier,
            )
        }
    }
}
