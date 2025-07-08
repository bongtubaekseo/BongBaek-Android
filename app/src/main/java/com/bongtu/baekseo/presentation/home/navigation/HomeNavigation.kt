package com.bongtu.baekseo.presentation.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import com.bongtu.baekseo.presentation.home.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
) {
    composable<Home> {
        HomeRoute(
            modifier = modifier,
        )
    }
}

@Serializable
data object Home: MainTabRoute