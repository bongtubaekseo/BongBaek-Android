package com.bongtu.baekseo.presentation.onboarding.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun OnBoardingSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val switchPadding = 1.dp
    val switchWidth = 54.dp
    val switchHeight = 28.dp
    val switchSize = switchHeight - switchPadding * 2

    val targetOffset = if (checked) switchWidth - switchSize - switchPadding * 2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = targetOffset,
        tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing,
        ),
    )

    Box(
        modifier = modifier
            .width(switchWidth)
            .height(switchHeight)
            .clip(shape = CircleShape)
            .background(if (checked) BongBaekTheme.colors.primaryNormal else BongBaekTheme.colors.gray400)
            .noRippleClickable {
                onCheckedChange(!checked)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(switchPadding),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent),
            )

            Box(
                modifier = Modifier
                    .size(switchSize)
                    .clip(shape = CircleShape)
                    .border(
                        width = 1.dp,
                        color = BongBaekTheme.colors.gray100,
                        shape = CircleShape,
                    )
                    .background(color = BongBaekTheme.colors.white),
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingSwitchPreview() {
    var isChecked by remember { mutableStateOf(false) }

    BongBaekTheme {
        OnBoardingSwitch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
            },
        )
    }
}