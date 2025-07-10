package com.bongtu.baekseo.core.designsystem.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bongtu.baekseo.R
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun LottieFiniteOverlay(
    @RawRes lottieRes: Int,
    modifier: Modifier = Modifier,
    iterations: Int = 1,
    onFinished: (Boolean) -> Unit = {},
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val lottieAnimState = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        composition?.let {
            lottieAnimState.animate(it, iterations = iterations)
            onFinished(true)
        }
    }

    LottieAnimation(
        composition = composition,
        progress = {
            lottieAnimState.progress
        },
        modifier = modifier,
    )
}

@Composable
fun LottieInfiniteOverlay(
    @RawRes lottieRes: Int,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    LottieAnimation(
        composition = composition,
        progress = {
            progress
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun LottieOverlayPreview() {
    BongBaekTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LottieInfiniteOverlay(
                lottieRes = R.raw.lottie_find_amount,
            )
        }
    }
}
