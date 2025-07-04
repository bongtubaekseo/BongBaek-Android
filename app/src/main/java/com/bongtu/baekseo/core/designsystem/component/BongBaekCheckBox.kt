package com.bongtu.baekseo.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_checkbox_unselected
import com.bongtu.baekseo.core.common.type.CheckBoxType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 * Bong baek check box
 *
 * 온보딩과 금액 추천에서 사용하는 component
 *
 * @param isChecked - 선택 여부
 * @param onClick - click event
 * @param checkBoxType - check box 타입 (CheckBoxType.PRIMARY, CheckBoxType.GRAY)
 */
@Composable
fun BongBaekCheckBox(
    isChecked: Boolean,
    onClick: (Boolean) -> Unit,
    checkBoxType: CheckBoxType,
    modifier: Modifier = Modifier,
) {
    val checkBoxSize = checkBoxType.size.dp

    Box(
        modifier = modifier.noRippleClickable {
            onClick(!isChecked)
        }
    ) {
        AnimatedVisibility(
            visible = isChecked,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = checkBoxType.checkedIcon),
                contentDescription = null,
                modifier = Modifier.size(checkBoxSize),
                tint = Color.Unspecified,
            )
        }
        AnimatedVisibility(
            visible = !isChecked,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_checkbox_unselected),
                contentDescription = null,
                modifier = Modifier.size(checkBoxSize),
                tint = Color.Unspecified,
            )
        }
    }
}

@Preview
@Composable
private fun BongBaekCheckBoxPreview() {
    var isChecked by remember { mutableStateOf(false) }

    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BongBaekCheckBox(
                isChecked = isChecked,
                onClick = {
                    isChecked = it
                },
                checkBoxType = CheckBoxType.PRIMARY,
            )
            BongBaekCheckBox(
                isChecked = isChecked,
                onClick = {
                    isChecked = it
                },
                checkBoxType = CheckBoxType.GRAY,
            )
        }
    }
}