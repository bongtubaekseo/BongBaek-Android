package com.bongtu.baekseo.presentation.home.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.contents.navigation.navigateToContentsDetail
import com.bongtu.baekseo.presentation.home.HomeRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    navigateToRecord: () -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToContents: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Home> {
        HomeRoute(
            navigateToRecord = navigateToRecord,
            navigateToRecommend = navigateToRecommend,
            navigateToContents = navigateToContents,
            navigateToContentsDetail = {
                navController.navigateToContentsDetail(
                    contentId = it.contentId,
                    eventType = it.contentCategory,
                    title = it.contentTitle,
                    date = it.createdAt,
                    imageUrls = it.imageUrls,
                )
            },
            modifier = modifier,
        )
    }
}

@Serializable
data object Home : MainTabRoute
