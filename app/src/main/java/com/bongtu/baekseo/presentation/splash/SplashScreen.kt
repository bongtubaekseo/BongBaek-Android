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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_splash_logo
import com.bongtu.baekseo.R.drawable.ic_splash_name
import com.bongtu.baekseo.R.string.market_url
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.getVersionName
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
    val context = LocalContext.current
    val packageName = context.packageName
    val marketUrl = stringResource(market_url, packageName)
    val isUpdateDialogVisible by viewModel.isUpdateDialogVisible.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        val appVersion = context.getVersionName()
        // TODO: 구조 변경
        delay(1500)
        viewModel.postTokenReissue()
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
            bongbaekColors.gray750,
            bongbaekColors.gray900,
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
                tint = BongBaekTheme.colors.white,
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
