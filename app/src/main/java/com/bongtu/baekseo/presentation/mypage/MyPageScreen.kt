package com.bongtu.baekseo.presentation.mypage

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import coil3.compose.AsyncImage
import com.bongtu.baekseo.BuildConfig
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.R.drawable.ic_mypage_book
import com.bongtu.baekseo.R.drawable.ic_mypage_information
import com.bongtu.baekseo.R.drawable.ic_mypage_intersect
import com.bongtu.baekseo.R.drawable.ic_mypage_key
import com.bongtu.baekseo.R.drawable.img_mypage_profile
import com.bongtu.baekseo.R.string.mypage_app_version
import com.bongtu.baekseo.R.string.mypage_inquiry
import com.bongtu.baekseo.R.string.mypage_logout
import com.bongtu.baekseo.R.string.mypage_personal_privacy
import com.bongtu.baekseo.R.string.mypage_profile_edit_button
import com.bongtu.baekseo.R.string.mypage_service_terms
import com.bongtu.baekseo.R.string.mypage_service_title
import com.bongtu.baekseo.R.string.mypage_topbar_title
import com.bongtu.baekseo.R.string.mypage_user_birth
import com.bongtu.baekseo.R.string.mypage_user_income
import com.bongtu.baekseo.R.string.mypage_version_format
import com.bongtu.baekseo.R.string.mypage_withdrawal
import com.bongtu.baekseo.core.common.type.DialogType
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDialog
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.UrlConstant
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.openUrl
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageSideEffect
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageSideEffect.MainSideEffect.RestartApp
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageUiState
import kotlinx.coroutines.flow.filterIsInstance

@Composable
fun MyPageRoute(
    navigateUp: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToWithdraw: () -> Unit,
    onRestartApp: (Boolean) -> Unit,
    viewModel: MyPageViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchUserProfile()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .filterIsInstance<MyPageSideEffect.MainSideEffect>()
            .collect { sideEffect ->
                when (sideEffect) {
                    RestartApp -> onRestartApp(false)
                }
            }
    }

    MyPageScreen(
        uiState = uiState,
        navigateUp = navigateUp,
        navigateToEditProfile = navigateToEditProfile,
        navigateToWithdraw = navigateToWithdraw,
        onLogoutClick = viewModel::logout,
        onInquiryClick = { context.openUrl(UrlConstant.INQUIRY_URL) },
        onTermsClick = { context.openUrl(UrlConstant.TERMS_URL) },
        onPrivacyClick = { context.openUrl(UrlConstant.PRIVACY_URL) },
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageUiState,
    navigateUp: () -> Unit,
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
            .background(color = BongBaekTheme.colors.bgDisplayPrimary),
    ) {
        Column(
            modifier = Modifier
                .background(BongBaekTheme.colors.bgDisplayPrimary)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                BongBaekTopBar(
                    title = stringResource(mypage_topbar_title),
                    topBarType = TopBarType.LEADING_ICON,
                    modifier = Modifier
                        .background(BongBaekTheme.colors.bgDisplayPrimary)
                        .statusBarsPadding(),
                    leadingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_arrow_back),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(12.dp)
                                .noRippleClickable(onClick = navigateUp),
                            tint = BongBaekTheme.colors.iconInteractiveDefault,
                        )
                    },
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
                        horizontal = 60.dp,
                        vertical = 14.dp,
                    )
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(mypage_logout),
                    modifier = Modifier
                        .noRippleClickable {
                            isLogoutDialogVisible = true
                        },
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body2Regular14,
                )

                Text(
                    text = stringResource(mypage_withdrawal),
                    modifier = Modifier
                        .noRippleClickable(onClick = navigateToWithdraw),
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body2Regular14,
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
    imageUrl: String? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplayPrimary,
                shape = RoundedCornerShape(
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp,
                ),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(110.dp)
                .clip(RoundedCornerShape(20.dp)),
            error = painterResource(img_mypage_profile),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = userName,
            modifier = Modifier.padding(vertical = 16.dp),
            color = BongBaekTheme.colors.txtDisplayPrimary,
            maxLines = 1,
            style = BongBaekTheme.typography.headBold24,
        )

        Text(
            text = stringResource(mypage_profile_edit_button),
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.statusFocused,
                    shape = RoundedCornerShape(20.dp),
                )
                .noRippleClickable(onProfileEditButtonClick)
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp,
                ),
            color = BongBaekTheme.colors.txtInteractiveInverse,
            textAlign = TextAlign.Center,
            style = BongBaekTheme.typography.captionRegular12,
        )

        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = 16.dp,
                    end = 20.dp,
                    bottom = 26.dp,
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
                    .padding(
                        bottom = 8.dp,
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(mypage_user_birth),
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body1Medium14,
                )

                Text(
                    text = userBirth,
                    color = BongBaekTheme.colors.txtDisplaySecondary,
                    style = BongBaekTheme.typography.body1Medium14,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(mypage_user_income),
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
) {
    Text(
        text = stringResource(mypage_service_title),
        modifier = Modifier
            .padding(
                start = 20.dp,
                top = 26.dp,
                end = 20.dp,
            ),
        color = BongBaekTheme.colors.txtInteractiveSecondary,
        style = BongBaekTheme.typography.titleSemiBold18,
    )

    Column(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 30.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(22.dp),
    ) {
        ServiceItem(
            iconRes = ic_mypage_intersect,
            titleRes = mypage_app_version,
            trailingIcon = {
                Text(
                    text = stringResource(mypage_version_format, BuildConfig.VERSION_NAME),
                    color = BongBaekTheme.colors.iconInteractiveDefault,
                    style = BongBaekTheme.typography.body1Medium16,
                )
            }
        )

        ServiceItem(
            iconRes = ic_mypage_information,
            titleRes = mypage_inquiry,
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
            iconRes = ic_mypage_book,
            titleRes = mypage_service_terms,
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
            iconRes = ic_mypage_key,
            titleRes = mypage_personal_privacy,
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
            .fillMaxWidth(),
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
private fun MyPageScreenPreview() {
    BongBaekTheme {
        MyPageScreen(
            uiState = MyPageUiState(
                userName = "봉투백서의겸손한야수",
                userBirth = "2000-01-05",
                userIncome = IncomeType.NONE,
            ),
            navigateUp = {},
            navigateToEditProfile = {},
            navigateToWithdraw = {},
            onLogoutClick = {},
            onInquiryClick = {},
            onTermsClick = {},
            onPrivacyClick = {},
        )
    }
}
