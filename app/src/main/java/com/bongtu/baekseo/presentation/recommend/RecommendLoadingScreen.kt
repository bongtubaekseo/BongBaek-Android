package com.bongtu.baekseo.presentation.recommend

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.raw.lottie_find_amount
import com.bongtu.baekseo.R.string.recommendation_loading_description
import com.bongtu.baekseo.R.string.recommendation_loading_title
import com.bongtu.baekseo.R.string.recommendation_loading_topbar
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.LottieFiniteOverlay
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

private const val MIN_LOOP_COUNT = 2

@Composable
fun RecommendLoadingRoute(
    navigateToResult: () -> Unit,
    viewModel: RecommendViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        // 뒤로가기 버튼 방지 TODO: 뭔가 더 깔끔한 방법 찾아야 할 듯
    }

    RecommendLoadingScreen(
        name = uiState.username,
        isNetworkDone = uiState.loadState is UiState.Success,
        onNavigateToResult = navigateToResult,
        modifier = modifier,
    )
}

@Composable
private fun RecommendLoadingScreen(
    name: String,
    isNetworkDone: Boolean,
    onNavigateToResult: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.bgDisplayPrimary)
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
            LottieFiniteOverlay(
                lottieRes = lottie_find_amount,
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .aspectRatio(1f)
                    .clipToBounds(),
                iterations = MIN_LOOP_COUNT,
                onFinished = {
                    if (it && isNetworkDone) onNavigateToResult()
                },
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = stringResource(recommendation_loading_title, name),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.txtDisplayPrimary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(recommendation_loading_description),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.txtDisplayTertiary,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendLoadingScreenPreview() {
    BongBaekTheme {
        RecommendLoadingScreen(
            name = "김종명",
            isNetworkDone = false,
            onNavigateToResult = {},
        )
    }
}
