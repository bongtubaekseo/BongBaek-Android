package com.bongtu.baekseo.presentation.home.navigation

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    navigateToRecommend: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToSchedule: () -> Unit,
    bottomPadding: Dp,
    modifier: Modifier = Modifier,
) {
    composable<Home> {
        HomeRoute(
            navigateToRecommend = navigateToRecommend,
            navigateToEdit = navigateToEdit,
            navigateToSchedule = navigateToSchedule,
            bottomPadding = bottomPadding,
            modifier = modifier,
        )
    }
}

@Serializable
data object Home : MainTabRoute
