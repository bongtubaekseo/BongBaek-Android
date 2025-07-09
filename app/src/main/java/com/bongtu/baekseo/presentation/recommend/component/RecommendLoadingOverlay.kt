package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R
import com.bongtu.baekseo.R.string.recommendation_loading_description
import com.bongtu.baekseo.R.string.recommendation_loading_title
import com.bongtu.baekseo.R.string.recommendation_loading_topbar
import com.bongtu.baekseo.R.raw.lottie_find_amount
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.LottieOverlay
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendLoadingOverlay(
    name: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        BongBaekTopBar(
            title = stringResource(recommendation_loading_topbar),
            topBarType = TopBarType.TEXT_ONLY_CENTER,
            modifier = Modifier
                .align(Alignment.TopCenter),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieOverlay(
                lottieRes = lottie_find_amount,
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .aspectRatio(1f)
                    .clipToBounds(),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(recommendation_loading_title, name),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.gray100,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(recommendation_loading_description),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendLoadingOverlayPreview() {
    BongBaekTheme {
        RecommendLoadingOverlay(
            name = "김종명",
        )
    }
}
