package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_kakao
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_age
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_privacy
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_service
import com.bongtu.baekseo.R.string.onboarding_description
import com.bongtu.baekseo.R.string.onboarding_login_information
import com.bongtu.baekseo.R.string.onboarding_personal_privacy
import com.bongtu.baekseo.R.string.onboarding_term_of_use
import com.bongtu.baekseo.R.string.onboarding_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.OnBoardingType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.OnBoardingAgree
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingBottomSheet
import kotlinx.coroutines.launch

@Composable
fun OnBoardingRoute(
    modifier: Modifier = Modifier,
) {
    var screenState by remember { mutableStateOf(OnBoardingType.LOGIN) }

    when (screenState) {
        OnBoardingType.LOGIN -> {
            OnBoardingLoginScreen(
                modifier = modifier,
                onNext = {
                    screenState = OnBoardingType.SETTING
                }
            )
        }
        OnBoardingType.SETTING -> {
            OnBoardingSettingScreen(
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingLoginScreen(
    onNext : () -> Unit,
    modifier: Modifier = Modifier,
) {
    val underlineColor = BongBaekTheme.colors.gray400

    var allChecked by remember { mutableStateOf(false) }
    val items = remember {
        mutableStateListOf(
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_age,
                isDescription = false,
                isArrowVisible = false,
            ),
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_service,
                isDescription = false,
                isArrowVisible = true,
            ),
            OnBoardingAgree(
                titleRes = onboarding_bottom_sheet_check_privacy,
                isDescription = false,
                isArrowVisible = true,
            ),
        )
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(top = 120.dp),
            ) {
                Text(
                    text =  buildAnnotatedString {
                        withStyle(style = SpanStyle(color = BongBaekTheme.colors.primaryNormal)) {
                            append("경조사비")
                        }
                        append(" 고민 끝,\n봉투백서에 오신 것을\n환영합니다!")
                    },
                    style = BongBaekTheme.typography.headBold26,
                    color = BongBaekTheme.colors.white,
                )

                Text(
                    text = stringResource(id = onboarding_description),
                    modifier = Modifier.padding(top = 30.dp),
                    style = BongBaekTheme.typography.body2Regular16,
                    color = BongBaekTheme.colors.gray300,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BongBaekButton(
                    title = "카카오 로그인",
                    onClick = {
                        isBottomSheetVisible = true
                    },
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
        if (isBottomSheetVisible) {
            OnBoardingBottomSheet(
                items = items,
                allChecked = allChecked,
                onAllCheckedChange = { checked ->
                    allChecked = checked
                    items.forEachIndexed { index, _ ->
                        items[index] = items[index].copy(isChecked = checked)
                    }
                },
                onItemCheckedChange = { index, checked ->
                    items[index] = items[index].copy(isChecked = checked)
                    allChecked = items.all { it.isChecked }
                },
                onNextClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            isBottomSheetVisible = false
                            onNext()
                        }
                    }
                },
                onDismissRequest = {
                    isBottomSheetVisible = false
                },
                modifier = Modifier.align(Alignment.BottomCenter),
                sheetState = sheetState,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {
    BongBaekTheme {
        OnBoardingLoginScreen(
            onNext = {}
        )
    }
}