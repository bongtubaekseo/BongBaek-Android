package com.bongtu.baekseo.presentation.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_google
import com.bongtu.baekseo.R.drawable.ic_kakao
import com.bongtu.baekseo.R.drawable.ic_login_logo
import com.bongtu.baekseo.R.string.button_google
import com.bongtu.baekseo.R.string.button_kakao
import com.bongtu.baekseo.R.string.login_bottom_sheet_check_age
import com.bongtu.baekseo.R.string.login_bottom_sheet_check_privacy
import com.bongtu.baekseo.R.string.login_bottom_sheet_check_service
import com.bongtu.baekseo.R.string.login_description
import com.bongtu.baekseo.R.string.login_login_information
import com.bongtu.baekseo.R.string.login_personal_privacy
import com.bongtu.baekseo.R.string.login_term_of_use
import com.bongtu.baekseo.R.string.login_title
import com.bongtu.baekseo.R.string.market_url
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DialogType
import com.bongtu.baekseo.core.common.type.LoginType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDialog
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.UrlConstant
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.openUrl
import com.bongtu.baekseo.presentation.login.LoginContract.LoginSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.login.LoginContract.LoginUiState
import com.bongtu.baekseo.presentation.login.component.LoginBottomSheet
import com.bongtu.baekseo.presentation.login.model.LoginAgree
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    navigateToOnBoarding: (String, String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val isUpdateDialogVisible by viewModel.isUpdateDialogVisible.collectAsStateWithLifecycle()
    val packageName = context.packageName
    val marketUrl = stringResource(market_url, packageName)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToHome -> navigateToHome()
                }
            }
    }

    LaunchedEffect(uiState.loadState) {
        when (uiState.loadState) {
            is UiState.Empty -> {}
            is UiState.Failure -> {}
            is UiState.Loading -> {}
            is UiState.Success -> {
                isBottomSheetVisible = true
                viewModel.updateLoginUiState(UiState.Empty)
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onKakaoLoginClick = {
            KakaoLauncher(
                context = context,
                onTokenReceived = { token ->
                    viewModel.socialLogin(
                        oauthProvider = LoginType.KAKAO.label,
                        idToken = token,
                    )
                },
                onError = {
                    // TODO: 에러 추후 구현
                }
            ).startKakaoLogin()
        },
        onGoogleLoginClick = {
            coroutineScope.launch {
                val idToken = GoogleLauncher.startGoogleLogin(
                    activity = (context as Activity),
                )
                idToken?.let {
                    viewModel.socialLogin(
                        oauthProvider = LoginType.GOOGLE.label,
                        idToken = idToken,
                    )
                }
            }
        },
        isBottomSheetVisible = isBottomSheetVisible,
        onBottomSheetVisibleChange = {
            isBottomSheetVisible = it
        },
        onPrivacyClick = { context.openUrl(UrlConstant.PRIVACY_URL) },
        onTermsClick = { context.openUrl(UrlConstant.TERMS_URL) },
        onNextClick = navigateToOnBoarding,
        modifier = modifier,
    )

    if (isUpdateDialogVisible) {
        BongBaekDialog(
            dialogType = DialogType.ERROR_UPDATE,
            onDismissRequest = { (context as? Activity)?.finish() },
            onConfirmClick = { context.openUrl(marketUrl) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onKakaoLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    isBottomSheetVisible: Boolean,
    onBottomSheetVisibleChange: (Boolean) -> Unit,
    onPrivacyClick: () -> Unit,
    onTermsClick: () -> Unit,
    onNextClick: (String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val checkedStates = remember { mutableStateListOf(false, false, false) }
    val items = persistentListOf(
        LoginAgree(
            titleRes = login_bottom_sheet_check_age,
            isDescription = false,
            isArrowVisible = false,
        ),
        LoginAgree(
            titleRes = login_bottom_sheet_check_service,
            isDescription = false,
            isArrowVisible = true,
            url = UrlConstant.TERMS_URL,
        ),
        LoginAgree(
            titleRes = login_bottom_sheet_check_privacy,
            isDescription = false,
            isArrowVisible = true,
            url = UrlConstant.PRIVACY_URL,
        ),
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.bgDisplaySecondary)
            .padding(horizontal = 20.dp)
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 120.dp,
                ),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_login_logo),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = login_title),
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.txtDisplayPrimary,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = login_description),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    bottom = 80.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BongBaekButton(
                title = stringResource(id = button_google),
                onClick = onGoogleLoginClick,
                buttonType = ButtonType.GOOGLE,
                modifier = Modifier
                    .padding(
                        bottom = 12.dp,
                    )
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BongBaekTheme.colors.googleGray,
                        shape = RoundedCornerShape(10.dp),
                    ),
                enabled = uiState.loadState !is UiState.Loading,
                textStyle = BongBaekTheme.typography.robotoMedium14,
                paddingValues = PaddingValues(vertical = 15.dp),
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_google),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(end = 15.dp),
                    )
                },
            )

            BongBaekButton(
                title = stringResource(id = button_kakao),
                onClick = onKakaoLoginClick,
                buttonType = ButtonType.KAKAO,
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.loadState !is UiState.Loading,
                textStyle = BongBaekTheme.typography.titleSemiBold18,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_kakao),
                        contentDescription = null,
                        tint = BongBaekTheme.colors.black,
                        modifier = Modifier
                            .padding(end = 10.dp),
                    )
                },
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = login_login_information),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.txtDisplayTertiary,
            )

            Row(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                    ),
            ) {
                Text(
                    text = stringResource(id = login_personal_privacy),
                    textDecoration = TextDecoration.Underline,
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    modifier = Modifier.noRippleClickable(onClick = onPrivacyClick),
                )

                Spacer(modifier = Modifier.width(30.dp))

                Text(
                    text = stringResource(id = login_term_of_use),
                    textDecoration = TextDecoration.Underline,
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    modifier = Modifier.noRippleClickable(onClick = onTermsClick),
                )
            }
        }
    }

    if (isBottomSheetVisible) {
        LoginBottomSheet(
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
                        onNextClick(uiState.oauthId, uiState.oauthProvider)
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
private fun LoginScreenPreview() {
    BongBaekTheme {
        LoginScreen(
            uiState = LoginUiState(),
            onKakaoLoginClick = {},
            isBottomSheetVisible = false,
            onBottomSheetVisibleChange = {},
            onNextClick = { _, _ -> },
            onPrivacyClick = {},
            onTermsClick = {},
            onGoogleLoginClick = {},
        )
    }
}
