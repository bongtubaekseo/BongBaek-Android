package com.bongtu.baekseo.presentation.recommend

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
import com.bongtu.baekseo.R.raw.lottie_envelope
import com.bongtu.baekseo.R.string.recommendation_result_topbar
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.LottieFiniteOverlay
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.recommend.component.RecommendExpenseCard
import com.bongtu.baekseo.presentation.recommend.component.RecommendResultContent

private const val ENVELOPE_RATIO = 320 / 331f

@Composable
fun RecommendResultScreen(
    expense: Int,
    eventType: EventType,
    onConfirmClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isLottieEnded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
                .padding(
                    horizontal = 20.dp,
                )
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
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
                    event = eventType,
                    expense = expense,
                    isLottieEnded = isLottieEnded,
                    modifier = Modifier
                        .matchParentSize(),
                )
            }

            RecommendResultContent(
                expense = expense,
                isLottieEnded = isLottieEnded,
                onConfirmClick = onConfirmClick,
                onEditClick = onEditClick,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendResultScreenPreview() {
    BongBaekTheme {
        RecommendResultScreen(
            expense = 125_000,
            eventType = EventType.WEDDING,
            onConfirmClick = {},
            onEditClick = {},
        )
    }
}
