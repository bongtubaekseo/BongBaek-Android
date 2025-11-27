package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_down
import com.bongtu.baekseo.R.string.edit_is_attend_dropdown_placeholder
import com.bongtu.baekseo.core.designsystem.component.dropdownmenu.BongBaekDropdownMenu
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.edit.type.EditDropDownType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDropdownMenu(
    placeholder: String,
    menuItems: ImmutableList<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    isEditable: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    val hasValue = selectedItem.isNotBlank()

    val text = if (hasValue) selectedItem else placeholder
    val dropDownType = EditDropDownType.from(
        hasValue = hasValue,
        expanded = expanded,
    )

    val colorState = dropDownType.getColorState(isEditable = isEditable)

    val rotation by animateFloatAsState(
        targetValue = if (expanded) -180f else 0f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
    )

    BongBaekDropdownMenu(
        expanded = expanded,
        onExpandedChange = {
            if (isEditable) expanded = !expanded
        },
        items = menuItems,
        selectedItem = selectedItem,
        onDismissRequest = { expanded = false },
        onItemSelect = { onItemSelected(it) },
        label = { it },
        maxItemSize = menuItems.size,
        offset = DpOffset(0.dp, 12.dp),
    ) {
        Row(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
                .background(
                    color = BongBaekTheme.colors.bgFieldSecondary,
                    shape = RoundedCornerShape(10.dp),
                )
                .border(
                    width = 1.dp,
                    color = colorState.borderColor,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = text,
                style = BongBaekTheme.typography.body1Medium16,
                color = colorState.textColor,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_down),
                contentDescription = null,
                modifier = Modifier.rotate(rotation),
                tint = colorState.iconColor,
            )
        }
    }
}

@Preview
@Composable
private fun EditFormFieldDropDownPreview() {
    BongBaekTheme {
        EditDropdownMenu(
            placeholder = stringResource(edit_is_attend_dropdown_placeholder),
            menuItems = persistentListOf("출석", "결석"),
            selectedItem = "출석",
            onItemSelected = {},
        )
    }
}
