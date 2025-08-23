package com.bongtu.baekseo.presentation.home.navigation

import androidx.compose.ui.Modifier
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
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Home> {
        HomeRoute(
            navigateToRecommend = navigateToRecommend,
            navigateToEdit = navigateToEdit,
            navigateToSchedule = navigateToSchedule,
            modifier = modifier,
        )
    }
}

@Serializable
data object Home : MainTabRoute
