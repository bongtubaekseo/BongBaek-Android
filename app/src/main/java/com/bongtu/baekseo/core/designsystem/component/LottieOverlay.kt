package com.bongtu.baekseo.core.designsystem.component

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bongtu.baekseo.R
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun LottieOverlay(
    @RawRes lottieRes: Int,
    modifier: Modifier = Modifier,
    iterations: Int = LottieConstants.IterateForever,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
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
private fun NapzakLoadingOverlayPreview() {
    BongBaekTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LottieOverlay(
                lottieRes = R.raw.lottie_find_amount,
            )
        }
    }
}
