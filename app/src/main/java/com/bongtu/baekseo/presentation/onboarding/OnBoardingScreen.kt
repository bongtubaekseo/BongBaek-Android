package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_kakao
import com.bongtu.baekseo.R.string.onboarding_description
import com.bongtu.baekseo.R.string.onboarding_login_information
import com.bongtu.baekseo.R.string.onboarding_personal_privacy
import com.bongtu.baekseo.R.string.onboarding_term_of_use
import com.bongtu.baekseo.R.string.onboarding_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun OnBoardingRoute(
    modifier: Modifier = Modifier,
) {
    OnBoardingScreen(
        modifier = modifier,
    )
}

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
) {
    val underlineColor = BongBaekTheme.colors.gray400

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.padding(top = 120.dp),
        ) {
            Text(
                text = stringResource(id = onboarding_title),
                style = BongBaekTheme.typography.headBold26,
                color = BongBaekTheme.colors.white,
            )

            Text(
                text = stringResource(id = onboarding_description),
                modifier = Modifier.padding(top = 30.dp),
                style = BongBaekTheme.typography.body2Regular16,
                color = BongBaekTheme.colors.white,
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BongBaekButton(
                title = "카카오 로그인",
                onClick = { },
                buttonType = ButtonType.KAKAO,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = BongBaekTheme.typography.titleSemiBold18,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_kakao),
                        contentDescription = null,
                    )
                },
            )

            Text(
                text = stringResource(id = onboarding_login_information),
                modifier = Modifier.padding(top = 20.dp),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.white,
            )

            Row(
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 26.dp,
                ),
            ) {
                Text(
                    text = stringResource(id = onboarding_personal_privacy),
                    modifier = Modifier.drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height
                        drawLine(
                            color = underlineColor,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.gray300,
                )

                Spacer(modifier = Modifier.width(29.dp))

                Text(
                    text = stringResource(id = onboarding_term_of_use),
                    modifier = Modifier.drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height
                        drawLine(
                            color = underlineColor,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.gray300,
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    BongBaekTheme {
        OnBoardingScreen()
    }
}