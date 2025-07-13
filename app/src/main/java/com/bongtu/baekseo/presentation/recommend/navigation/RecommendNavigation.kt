package com.bongtu.baekseo.presentation.recommend.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.edit.type.EditType
import com.bongtu.baekseo.presentation.recommend.RecommendFinalRoute
import com.bongtu.baekseo.presentation.recommend.RecommendIntroScreen
import com.bongtu.baekseo.presentation.recommend.RecommendMainRoute
import com.bongtu.baekseo.presentation.recommend.RecommendResultRoute
import com.bongtu.baekseo.presentation.recommend.RecommendRoute
import com.bongtu.baekseo.presentation.recommend.RecommendViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToRecommend(navOptions: NavOptions? = null) =
    navigate(Recommend, navOptions)

fun NavGraphBuilder.recommendGraph(
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToRecord: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Recommend> {
        RecommendRoute(
            navigateToUp = navigateToUp,
            navigateToEdit = navigateToEdit,
            navigateToHome = navigateToHome,
            navigateToRecord = navigateToRecord,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.nestedRecommendGraph(
    navController: NavHostController,
    navigateToUp: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToRecord: () -> Unit,
    viewModel: RecommendViewModel,
    modifier: Modifier = Modifier,
) {
    composable<RecommendRoute.Intro> {
        RecommendIntroScreen(
            navigateToUp = navigateToUp,
            navigateToNext = {
                navController.navigate(RecommendRoute.Main)
            },
            modifier = modifier,
        )
    }

    composable<RecommendRoute.Main> {
        RecommendMainRoute(
            navigateToUp = navController::navigateUp,
            navigateToResult = {
                navController.navigate(
                    route = RecommendRoute.Result,
                    navOptions = navOptions {
                        popUpTo(RecommendRoute.Intro) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            viewModel = viewModel,
            modifier = modifier,
        )
    }

    composable<RecommendRoute.Result> {
        RecommendResultRoute(
            navigateToFinal = {
                navController.navigate(
                    route = RecommendRoute.Final,
                    navOptions = navOptions {
                        popUpTo(RecommendRoute.Final) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            navigateToEdit = navigateToEdit,
            viewModel = viewModel,
            modifier = modifier,
        )
    }

    composable<RecommendRoute.Final> {
        RecommendFinalRoute(
            navigateToHome = navigateToHome,
            navigateToRecord = navigateToRecord,
            modifier = modifier,
        )
    }
}

@Serializable
data object Recommend : MainTabRoute
