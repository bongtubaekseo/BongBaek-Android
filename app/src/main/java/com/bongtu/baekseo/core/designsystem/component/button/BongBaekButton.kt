package com.bongtu.baekseo.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun BongBaekButton(
    title: String,
    onClick: () -> Unit,
    buttonType: ButtonType,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = BongBaekTheme.typography.titleSemiBold20,
    shape: Shape = RoundedCornerShape(10.dp),
    paddingValues: PaddingValues = PaddingValues(15.dp),
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    val bongBaekColors = BongBaekTheme.colors
    val (backgroundColor, contentColor) = remember(buttonType, enabled) {
        when (buttonType) {
            ButtonType.PRIMARY -> {
                if (enabled) bongBaekColors.primaryNormal to bongBaekColors.white
                else bongBaekColors.primaryNormal.copy(alpha = .1f) to bongBaekColors.gray500
            }

            ButtonType.SECONDARY -> bongBaekColors.gray700 to bongBaekColors.white
            ButtonType.DELETE -> bongBaekColors.transparent to bongBaekColors.secondaryRed
            ButtonType.KAKAO -> bongBaekColors.kakaoYellow to bongBaekColors.black
        }
    }

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor,
        ),
        contentPadding = paddingValues,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            leadingIcon?.invoke()

            Text(
                text = title,
                style = textStyle,
            )
        }
    }
}

@Preview
@Composable
private fun BongBaekButtonPreview() {
    BongBaekTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BongBaekTheme.colors.gray900),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            BongBaekButton(
                title = "btn",
                onClick = { },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            BongBaekButton(
                title = "btn",
                onClick = { },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = false,
            )

            BongBaekButton(
                title = "btn",
                onClick = { },
                buttonType = ButtonType.SECONDARY,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            BongBaekButton(
                title = "btn",
                onClick = { },
                buttonType = ButtonType.DELETE,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BongBaekTheme.colors.secondaryRed,
                        shape = RoundedCornerShape(10.dp),
                    ),
            )

            BongBaekButton(
                title = "카카오 로그인",
                onClick = { },
                buttonType = ButtonType.KAKAO,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = BongBaekTheme.typography.titleSemiBold18,
                paddingValues = PaddingValues(16.5.dp),
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_kakao),
                        contentDescription = null,
                    )
                },
            )

            BongBaekButton(
                title = "지금 기록하기",
                onClick = { },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .wrapContentWidth(),
                textStyle = BongBaekTheme.typography.titleSemiBold16,
                paddingValues = PaddingValues(
                    horizontal = 29.dp,
                    vertical = 7.dp,
                ),
            )
        }
    }
}
