package com.bongtu.baekseo.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_splash_logo
import com.bongtu.baekseo.R.drawable.ic_splash_name
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.NavigateToOnBoarding
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.RestartApp
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    onRestartApp: (Boolean) -> Unit,
    navigateToOnBoarding: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        delay(1500)
        viewModel.initialize()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToHome -> navigateToHome()
                    is NavigateToOnBoarding -> navigateToOnBoarding()
                    is RestartApp -> onRestartApp(true)
                }
            }
    }

    SplashScreen(
        modifier = modifier,
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    val bongbaekColors = BongBaekTheme.colors
    val backgroundColors = remember {
        listOf(
            bongbaekColors.splashStart,
            bongbaekColors.splashEnd,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColors,
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = ic_splash_logo),
                contentDescription = null,
                modifier = Modifier.size(34.dp),
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = ic_splash_name),
                contentDescription = null,
                tint = BongBaekTheme.colors.txtInteractiveInverse,
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    BongBaekTheme {
        SplashScreen()
    }
}
