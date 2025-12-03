package com.bongtu.baekseo.presentation.setting

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import com.bongtu.baekseo.BuildConfig
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.R.drawable.ic_setting_ask
import com.bongtu.baekseo.R.drawable.ic_setting_lock
import com.bongtu.baekseo.R.drawable.ic_setting_terms
import com.bongtu.baekseo.R.drawable.ic_setting_version
import com.bongtu.baekseo.R.drawable.img_setting_profile
import com.bongtu.baekseo.R.string.setting
import com.bongtu.baekseo.R.string.setting_app_version
import com.bongtu.baekseo.R.string.setting_inquiry
import com.bongtu.baekseo.R.string.setting_logout
import com.bongtu.baekseo.R.string.setting_personal_privacy
import com.bongtu.baekseo.R.string.setting_profile_edit_button
import com.bongtu.baekseo.R.string.setting_service_terms
import com.bongtu.baekseo.R.string.setting_service_title
import com.bongtu.baekseo.R.string.setting_user_birth
import com.bongtu.baekseo.R.string.setting_user_income
import com.bongtu.baekseo.R.string.setting_version_format
import com.bongtu.baekseo.R.string.setting_withdrawal
import com.bongtu.baekseo.core.common.type.DialogType
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDialog
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.UrlConstant
import com.bongtu.baekseo.core.util.excludeTop
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.openUrl
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect.RestartApp
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingUiState

@Composable
fun SettingRoute(
    navigateToEditProfile: (String, String, IncomeType) -> Unit,
    navigateToWithdraw: () -> Unit,
    onRestartApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    RestartApp -> onRestartApp(false)
                }
            }
    }

    SettingScreen(
        uiState = uiState,
        navigateToEditProfile = {
            navigateToEditProfile(
                uiState.userName,
                uiState.userBirth,
                uiState.userIncome,
            )
        },
        navigateToWithdraw = navigateToWithdraw,
        onLogoutClick = viewModel::logout,
        onInquiryClick = { context.openUrl(UrlConstant.INQUIRY_URL) },
        onTermsClick = { context.openUrl(UrlConstant.TERMS_URL) },
        onPrivacyClick = { context.openUrl(UrlConstant.PRIVACY_URL) },
        modifier = modifier,
    )
}

@Composable
private fun SettingScreen(
    uiState: SettingUiState,
    navigateToEditProfile: () -> Unit,
    navigateToWithdraw: () -> Unit,
    onLogoutClick: () -> Unit,
    onInquiryClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isLogoutDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.bgDisplayPrimary)
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar.excludeTop()),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                BongBaekTopBar(
                    title = stringResource(setting),
                    topBarType = TopBarType.TEXT_ONLY_START,
                )

                ProfileSection(
                    userName = uiState.userName,
                    userBirth = DateFormatter.formatToKorean(uiState.userBirth),
                    userIncome = uiState.userIncome.uiLabel,
                    onProfileEditButtonClick = navigateToEditProfile,
                )

                ServiceSection(
                    onInquiryClick = onInquiryClick,
                    onTermsClick = onTermsClick,
                    onPrivacyClick = onPrivacyClick,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                    ),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(setting_logout),
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickable {
                            isLogoutDialogVisible = true
                        },
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body2Regular14,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = stringResource(setting_withdrawal),
                    modifier = Modifier
                        .weight(1f)
                        .noRippleClickable(onClick = navigateToWithdraw),
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body2Regular14,
                    textAlign = TextAlign.Center,
                )
            }
        }

        if (isLogoutDialogVisible) {
            BongBaekDialog(
                dialogType = DialogType.LOGOUT,
                onDismissRequest = {
                    isLogoutDialogVisible = false
                },
                onConfirmClick = {
                    isLogoutDialogVisible = false
                    onLogoutClick()
                },
            )
        }
    }
}

@Composable
private fun ProfileSection(
    userName: String,
    userBirth: String,
    userIncome: String,
    onProfileEditButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    top = 20.dp,
                )
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp)),
            error = painterResource(img_setting_profile),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = userName,
            modifier = Modifier
                .padding(
                    vertical = 12.dp,
                ),
            color = BongBaekTheme.colors.txtDisplayPrimary,
            style = BongBaekTheme.typography.headBold24,
            maxLines = 1,
        )

        Text(
            text = stringResource(setting_profile_edit_button),
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.statusFocused,
                    shape = RoundedCornerShape(20.dp),
                )
                .noRippleClickable(onProfileEditButtonClick)
                .padding(
                    horizontal = 20.dp,
                    vertical = 4.dp,
                ),
            color = BongBaekTheme.colors.txtInteractiveInverse,
            style = BongBaekTheme.typography.captionRegular12,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp,
                )
                .background(
                    color = BongBaekTheme.colors.bgDisplayCard,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(
                    vertical = 16.dp,
                    horizontal = 20.dp,
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(setting_user_birth),
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body1Medium14,
                )

                Text(
                    text = userBirth,
                    color = BongBaekTheme.colors.txtDisplaySecondary,
                    style = BongBaekTheme.typography.body1Medium14,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(setting_user_income),
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body1Medium14,
                )

                Text(
                    text = userIncome,
                    color = BongBaekTheme.colors.txtDisplaySecondary,
                    style = BongBaekTheme.typography.body1Medium14,
                )
            }
        }
    }
}

@Composable
private fun ServiceSection(
    onInquiryClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp),
    ) {
        Text(
            text = stringResource(setting_service_title),
            modifier = Modifier
                .padding(
                    vertical = 12.dp,
                ),
            color = BongBaekTheme.colors.txtDisplaySecondary,
            style = BongBaekTheme.typography.titleSemiBold18,
        )

        ServiceItem(
            iconRes = ic_setting_version,
            titleRes = setting_app_version,
            trailingIcon = {
                Text(
                    text = stringResource(setting_version_format, BuildConfig.VERSION_NAME),
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body1Medium16,
                )
            }
        )

        ServiceItem(
            iconRes = ic_setting_ask,
            titleRes = setting_inquiry,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable(onClick = onInquiryClick),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                )
            }
        )

        ServiceItem(
            iconRes = ic_setting_terms,
            titleRes = setting_service_terms,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable(onClick = onTermsClick),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                )
            }
        )

        ServiceItem(
            iconRes = ic_setting_lock,
            titleRes = setting_personal_privacy,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable(onClick = onPrivacyClick),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                )
            }
        )
    }
}

@Composable
private fun ServiceItem(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Text(
            text = stringResource(titleRes),
            modifier = Modifier
                .padding(
                    start = 12.dp,
                )
                .weight(1f),
            color = BongBaekTheme.colors.txtInteractiveSecondary,
            style = BongBaekTheme.typography.body1Medium16,
        )

        trailingIcon?.invoke()
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    BongBaekTheme {
        SettingScreen(
            uiState = SettingUiState(
                userName = "봉투백서의겸손한야수",
                userBirth = "1999-10-14",
                userIncome = IncomeType.OVER_200,
            ),
            navigateToEditProfile = {},
            navigateToWithdraw = {},
            onLogoutClick = {},
            onInquiryClick = {},
            onTermsClick = {},
            onPrivacyClick = {},
        )
    }
}
