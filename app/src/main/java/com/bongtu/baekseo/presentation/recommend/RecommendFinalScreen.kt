package com.bongtu.baekseo.presentation.recommend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.raw.lottie_envelope
import com.bongtu.baekseo.R.string.recommendation_record_description
import com.bongtu.baekseo.R.string.recommendation_record_title
import com.bongtu.baekseo.R.string.recommendation_record_topbar
import com.bongtu.baekseo.R.string.recommendation_to_home
import com.bongtu.baekseo.R.string.recommendation_to_record
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.LottieFiniteOverlay
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendFinalRoute(
    navigateToHome: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RecommendFinalScreen(
        onHomeButtonClick = navigateToHome,
        onRecordButtonClick = navigateToRecord,
        modifier = modifier,
    )
}

@Composable
private fun RecommendFinalScreen(
    onHomeButtonClick: () -> Unit,
    onRecordButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .padding(
                horizontal = 20.dp,
            )
            .systemBarsPadding(),
    ) {
        BongBaekTopBar(
            title = stringResource(recommendation_record_topbar),
            topBarType = TopBarType.TEXT_ONLY_CENTER,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieFiniteOverlay(
                lottieRes = lottie_envelope, // TODO: Lottie 수정
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .aspectRatio(1f)
                    .clipToBounds(),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(recommendation_record_title),
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.gray100,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(recommendation_record_description),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.gray300,
                textAlign = TextAlign.Center,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            BongBaekButton(
                title = stringResource(recommendation_to_home),
                onClick = onHomeButtonClick,
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth(),
            )
            BongBaekButton(
                title = stringResource(recommendation_to_record),
                onClick = onRecordButtonClick,
                buttonType = ButtonType.SECONDARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 46.dp,
                    ),
            )
        }
    }
}

@Preview
@Composable
private fun RecommendFinalScreenPreview() {
    BongBaekTheme {
        RecommendFinalScreen(
            onHomeButtonClick = {},
            onRecordButtonClick = {},
        )
    }
}
