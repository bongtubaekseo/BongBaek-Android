package com.bongtu.baekseo.presentation.splash.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSplash(navOptions: NavOptions? = null) =
    navigate(Splash, navOptions)

fun NavGraphBuilder.splashGraph(
    onRestartApp: (Boolean) -> Unit,
    navigateToOnBoarding: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Splash> {
        SplashRoute(
            onRestartApp = onRestartApp,
            navigateToOnBoarding = navigateToOnBoarding,
            navigateToHome = navigateToHome,
            modifier = modifier,
        )
    }
}

@Serializable
data object Splash : Route
