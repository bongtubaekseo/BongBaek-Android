package com.bongtu.baekseo.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_select
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun OnBoardingButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (selected) BongBaekTheme.colors.btnInteractiveDisabled else BongBaekTheme.colors.btnInteractiveTertiary
    val borderColor =
        if (selected) BongBaekTheme.colors.statusFocused else BongBaekTheme.colors.borderFieldDefault

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = backgroundColor)
            .noRippleClickable(onClick)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                start = 18.dp,
                end = 12.dp,
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = BongBaekTheme.typography.body2Regular14,
            color = BongBaekTheme.colors.txtInteractivePrimary,
        )

        Spacer(modifier = Modifier.weight(1f))

        if (selected) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_select),
                contentDescription = null,
                tint = BongBaekTheme.colors.statusFocused,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingButtonPreview() {
    var selected by remember { mutableStateOf(true) }

    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            OnBoardingButton(
                title = "월 200만원 미만",
                selected = selected,
                onClick = {
                    if (!selected) selected = true
                },
            )

            OnBoardingButton(
                title = "월 200만원 이상",
                selected = !selected,
                onClick = {
                    if (selected) selected = false
                },
            )
        }
    }
}
