package com.bongtu.baekseo.presentation.mypage

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
import com.bongtu.baekseo.R.string.mypage_withdrawal
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun MyPageRoute(
    navigateUp: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToWithDraw: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyPageScreen(
        navigateUp = navigateUp,
        navigateToEditProfile = navigateToEditProfile,
        navigateToWithDraw = navigateToWithDraw,
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    navigateUp: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToWithDraw: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        BongBaekTopBar(
            title = stringResource(mypage_topbar_title),
            topBarType = TopBarType.LEADING_ICON,
            modifier = Modifier
                .statusBarsPadding(),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(onClick = navigateUp),
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BongBaekTheme.colors.gray800),
        ) {
            ProfileSection(
                userName = "봉투백서의겸손한야수",
                userBirth = "2000년 01월 05일",
                userIncome = "없음",
                onProfileEditButtonClick = navigateToEditProfile,
            )

            ServiceSection(
                appVersion = "1.0.0",
                onInquiryClick = {},
                onTermsClick = {},
                onPrivacyClick = {},
            )

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 17.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(mypage_logout),
                    modifier = Modifier
                        .noRippleClickable(onClick = { /* TODO: 로그아웃 */ })
                        .padding(
                            vertical = 4.dp,
                            horizontal = 54.dp,
                        ),
                    color = BongBaekTheme.colors.gray400,
                    style = BongBaekTheme.typography.captionRegular12,
                )

                Text(
                    text = stringResource(mypage_withdrawal),
                    modifier = Modifier
                        .noRippleClickable(onClick = navigateToWithDraw)
                        .padding(
                            vertical = 4.dp,
                            horizontal = 54.dp,
                        )
                        .navigationBarsPadding(),
                    color = BongBaekTheme.colors.gray400,
                    style = BongBaekTheme.typography.captionRegular12,
                )
            }
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
                color = BongBaekTheme.colors.gray900,
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
                .padding(top = 24.dp)
                .clip(RoundedCornerShape(20.dp)),
            error = painterResource(img_mypage_profile),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = userName,
            modifier = Modifier.padding(vertical = 16.dp),
            color = BongBaekTheme.colors.gray100,
            maxLines = 1,
            style = BongBaekTheme.typography.headBold24,
        )

        Text(
            text = stringResource(mypage_profile_edit_button),
            modifier = Modifier
                .padding(top = 16.dp)
                .background(
                    color = BongBaekTheme.colors.primaryNormal,
                    shape = RoundedCornerShape(20.dp),
                )
                .noRippleClickable(onProfileEditButtonClick)
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp,
                ),
            color = BongBaekTheme.colors.white,
            textAlign = TextAlign.Center,
            style = BongBaekTheme.typography.captionRegular12,
        )

        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = 24.dp,
                    end = 20.dp,
                    bottom = 26.dp,
                )
                .background(
                    color = BongBaekTheme.colors.gray750,
                    shape = RoundedCornerShape(20.dp),
                )
                .padding(
                    vertical = 14.dp,
                    horizontal = 20.dp,
                ),
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        bottom = 4.dp,
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(mypage_user_birth),
                    color = BongBaekTheme.colors.gray200,
                    style = BongBaekTheme.typography.captionRegular12,
                )

                Text(
                    text = userBirth,
                    color = BongBaekTheme.colors.gray100,
                    style = BongBaekTheme.typography.captionRegular12,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(mypage_user_income),
                    color = BongBaekTheme.colors.gray200,
                    style = BongBaekTheme.typography.captionRegular12,
                )

                Text(
                    text = userIncome,
                    color = BongBaekTheme.colors.gray100,
                    style = BongBaekTheme.typography.captionRegular12,
                )
            }
        }
    }
}

@Composable
private fun ServiceSection(
    appVersion: String,
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
        color = BongBaekTheme.colors.white,
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
                    text = appVersion,
                    color = BongBaekTheme.colors.gray400,
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
                    tint = BongBaekTheme.colors.gray400,
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
                    tint = BongBaekTheme.colors.gray400,
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
                    tint = BongBaekTheme.colors.gray400,
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
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = BongBaekTheme.colors.gray300,
        )

        Text(
            text = stringResource(titleRes),
            modifier = Modifier
                .padding(
                    start = 12.dp,
                )
                .weight(1f),
            color = BongBaekTheme.colors.gray200,
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
            navigateUp = {},
            navigateToEditProfile = {},
            navigateToWithDraw = {},
        )
    }
}