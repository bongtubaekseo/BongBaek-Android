package com.bongtu.baekseo.presentation.recommend

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.raw.lottie_envelope
import com.bongtu.baekseo.R.string.recommendation_result_location_blank
import com.bongtu.baekseo.R.string.recommendation_result_topbar
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.LottieFiniteOverlay
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.ResultSideEffect.NavigateToEdit
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.ResultSideEffect.NavigateToFinal
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendUiState
import com.bongtu.baekseo.presentation.recommend.component.RecommendExpenseCard
import com.bongtu.baekseo.presentation.recommend.component.RecommendResultContent
import kotlinx.coroutines.flow.filterIsInstance

private const val ENVELOPE_RATIO = 320 / 291f

@Composable
fun RecommendResultRoute(
    navigateToFinal: () -> Unit,
    navigateToEdit: () -> Unit,
    viewModel: RecommendViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .filterIsInstance<RecommendSideEffect.ResultSideEffect>()
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToFinal -> navigateToFinal()
                    is NavigateToEdit -> navigateToEdit()
                }
            }
    }

    BackHandler {
        // 뒤로가기 버튼 방지 TODO: 뭔가 더 깔끔한 방법 찾아야 할 듯
    }

    RecommendResultScreen(
        uiState = uiState,
        onConfirmClick = viewModel::saveEventInformation,
        onEditClick = viewModel::navigateToEdit,
        modifier = modifier,
    )
}

@Composable
private fun RecommendResultScreen(
    uiState: RecommendUiState,
    onConfirmClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isLottieEnded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val displayLocation = uiState.selectedPlace?.name
        ?: stringResource(recommendation_result_location_blank)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .statusBarsPadding(),
    ) {
        BongBaekTopBar(
            title = stringResource(recommendation_result_topbar),
            topBarType = TopBarType.TEXT_ONLY_CENTER,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                    )
                    .aspectRatio(ENVELOPE_RATIO)
                    .clipToBounds(),
                contentAlignment = Alignment.Center,
            ) {
                this@Column.AnimatedVisibility(
                    visible = !isLottieEnded,
                    exit = fadeOut(animationSpec = tween(500)),
                ) {
                    LottieFiniteOverlay(
                        lottieRes = lottie_envelope,
                        modifier = Modifier
                            .matchParentSize(),
                        iterations = 1,
                        onFinished = {
                            isLottieEnded = it
                        },
                    )
                }

                RecommendExpenseCard(
                    event = uiState.eventType!!,
                    expense = uiState.expense,
                    isLottieEnded = isLottieEnded,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .matchParentSize(),
                )
            }

            RecommendResultContent(
                expense = uiState.expense,
                minExpense = uiState.minExpense,
                maxExpense = uiState.maxExpense,
                event = uiState.eventType!!.label,
                location = displayLocation,
                isLottieEnded = isLottieEnded,
                onConfirmClick = onConfirmClick,
                onEditClick = onEditClick,
                modifier = Modifier,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendResultScreenPreview() {
    BongBaekTheme {
        RecommendResultScreen(
            uiState = RecommendUiState(),
            onConfirmClick = {},
            onEditClick = {},
        )
    }
}
