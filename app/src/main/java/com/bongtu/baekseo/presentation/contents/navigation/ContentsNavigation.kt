package com.bongtu.baekseo.presentation.contents.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.contents.ContentsDetailScreen
import com.bongtu.baekseo.presentation.contents.ContentsRoute
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

fun NavController.navigateToContents(navOptions: NavOptions? = null) =
    navigate(Contents, navOptions)

fun NavController.navigateToContentsDetail(
    contentId: String,
    eventType: String,
    title: String,
    date: String,
    imageUrls: List<String>,
    navOptions: NavOptions? = null,
) = navigate(
    ContentsDetail(
        contentId = contentId,
        eventType = eventType,
        title = title,
        date = date,
        imageUrls = imageUrls,
    ),
    navOptions,
)

fun NavGraphBuilder.contentsGraph(
    navController: NavController,
    navigateToUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Contents> {
        ContentsRoute(
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

    composable<ContentsDetail> { backStackEntry ->
        val contentsDetail = backStackEntry.toRoute<ContentsDetail>()

        ContentsDetailScreen(
            onBackClick = navigateToUp,
            eventType = contentsDetail.eventType,
            title = contentsDetail.title,
            date = contentsDetail.date,
            imageUrls = contentsDetail.imageUrls.toImmutableList(),
        )
    }
}

@Serializable
data object Contents : MainTabRoute

@Serializable
data class ContentsDetail(
    val contentId: String,
    val eventType: String,
    val title: String,
    val date: String,
    val imageUrls: List<String>,
) : Route
