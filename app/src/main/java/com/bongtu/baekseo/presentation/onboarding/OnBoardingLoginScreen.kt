package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_kakao
import com.bongtu.baekseo.R.drawable.img_onboarding
import com.bongtu.baekseo.R.string.button_kakao
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_age
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_privacy
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_service
import com.bongtu.baekseo.R.string.onboarding_description
import com.bongtu.baekseo.R.string.onboarding_login_information
import com.bongtu.baekseo.R.string.onboarding_personal_privacy
import com.bongtu.baekseo.R.string.onboarding_term_of_use
import com.bongtu.baekseo.R.string.onboarding_title_primary
import com.bongtu.baekseo.R.string.onboarding_title_white
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingBottomSheet
import com.bongtu.baekseo.presentation.onboarding.model.OnBoardingAgree
import com.bongtu.baekseo.presentation.onboarding.type.OnBoardingType
import kotlinx.coroutines.launch

@Composable
fun OnBoardingRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var screenState by remember { mutableStateOf(OnBoardingType.LOGIN) }
    val socialLoginState by viewModel.kakaoLoginState.collectAsStateWithLifecycle()
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToHome -> navigateToHome()
                }
            }
    }

    LaunchedEffect(socialLoginState) {
        when (socialLoginState) {
            SocialLoginState.Fail -> {
                // TODO: 카카오 로그인 실패
            }

            SocialLoginState.Idle -> {
                // TODO: 추후 구현
            }

            SocialLoginState.Success -> {
                isBottomSheetVisible = true
                viewModel.setUiStateIdle()
            }
        }
    }

    when (screenState) {
        OnBoardingType.LOGIN -> {
            OnBoardingLoginScreen(
                onKakaoLoginClick = {
                    OnBoardingLoginKakaoLauncher(
                        context = context,
                        onTokenReceived = { token ->
                            viewModel.loginWithKakao(token)
                        },
                        onError = {
                            // TODO 추후 구현
                        }
                    ).startKakaoLogin()
                },
                isBottomSheetVisible = isBottomSheetVisible,
                onBottomSheetVisibleChange = {
                    isBottomSheetVisible = it
                },
                onNext = {
                    screenState = OnBoardingType.SETTING
                },
                modifier = modifier,
            )
        }

        OnBoardingType.SETTING -> {
            OnBoardingSettingScreen(
                navigateToHome = navigateToHome,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingLoginScreen(
    onKakaoLoginClick: () -> Unit,
    isBottomSheetVisible: Boolean,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val checkedStates = remember { mutableStateListOf(false, false, false) }
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

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = img_onboarding),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
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
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = BongBaekTheme.colors.primaryNormal)) {
                            append(stringResource(id = onboarding_title_primary))
                        }
                        append(stringResource(id = onboarding_title_white))
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
                    title = stringResource(id = button_kakao),
                    onClick = {
                        onKakaoLoginClick()
                        // TODO: 이미 온 사용자이면 OnBoardingSetting으로 이동
                    },
                    buttonType = ButtonType.KAKAO,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = BongBaekTheme.typography.titleSemiBold18,
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_kakao),
                            contentDescription = null,
                            tint = BongBaekTheme.colors.black,
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
                        textDecoration = TextDecoration.Underline,
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.gray300,
                    )

                    Spacer(modifier = Modifier.width(30.dp))

                    Text(
                        text = stringResource(id = onboarding_term_of_use),
                        textDecoration = TextDecoration.Underline,
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.gray300,
                    )
                }
            }
        }
    }

    if (isBottomSheetVisible) {
        OnBoardingBottomSheet(
            items = items,
            checkedStates = checkedStates,
            onAllCheckedChange = { checked ->
                checkedStates.indices.forEach { index ->
                    checkedStates[index] = checked
                }
            },
            onItemCheckedChange = { index, checked ->
                checkedStates[index] = checked
            },
            onNextClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onBottomSheetVisibleChange(false)
                        onNext()
                    }
                }
            },
            onDismissRequest = {
                onBottomSheetVisibleChange(false)
            },
            sheetState = sheetState,
        )
    }
}

@Preview
@Composable
private fun OnBoardingScreenLoginPreview() {
    BongBaekTheme {
        OnBoardingLoginScreen(
            onKakaoLoginClick = {},
            isBottomSheetVisible = false,
            onBottomSheetVisibleChange = {},
            onNext = {},
        )
    }
}