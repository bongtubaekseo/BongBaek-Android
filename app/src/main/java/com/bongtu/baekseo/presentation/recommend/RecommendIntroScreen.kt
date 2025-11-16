package com.bongtu.baekseo.presentation.recommend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_recommend_protect
import com.bongtu.baekseo.R.drawable.img_recommend_bong
import com.bongtu.baekseo.R.string.recommendation_intro_description
import com.bongtu.baekseo.R.string.recommendation_intro_privacy
import com.bongtu.baekseo.R.string.recommendation_intro_start
import com.bongtu.baekseo.R.string.recommendation_intro_title
import com.bongtu.baekseo.R.string.recommendation_intro_topbar
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecommendIntroScreen(
    navigateToUp: () -> Unit,
    navigateToMain: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = BongBaekTheme.colors.bgDisplayPrimary),
    ) {
        BongBaekTopBar(
            title = stringResource(recommendation_intro_topbar),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(navigateToUp),
                )
            },
            modifier = Modifier
                .statusBarsPadding(),
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                ),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(recommendation_intro_title),
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(recommendation_intro_description),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.txtDisplayTertiary,
            )

            Spacer(modifier = Modifier.height(19.dp))

            Image(
                painter = painterResource(img_recommend_bong),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_recommend_protect),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = stringResource(recommendation_intro_privacy),
                    style = BongBaekTheme.typography.body2Regular14,
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BongBaekButton(
                title = stringResource(recommendation_intro_start),
                onClick = navigateToMain,
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp),
            )
        }
    }
}

@Preview
@Composable
private fun RecommendIntroScreenPreview() {
    BongBaekTheme {
        RecommendIntroScreen(
            navigateToUp = {},
            navigateToMain = {},
        )
    }
}
