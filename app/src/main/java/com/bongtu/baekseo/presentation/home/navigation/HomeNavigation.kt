package com.bongtu.baekseo.presentation.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.home.HomeDefaultRoute
import com.bongtu.baekseo.presentation.home.HomeNavHost
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Default
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    setBottomBarVisible: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Home> {
        HomeNavHost(
            setBottomBarVisible = setBottomBarVisible,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.homeDefaultGraph(
    navigateToSchedule: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Default> {
        HomeDefaultRoute(
            navigateToSchedule = navigateToSchedule,
            modifier = modifier,
        )
    }
}

@Serializable
data object Home : MainTabRoute