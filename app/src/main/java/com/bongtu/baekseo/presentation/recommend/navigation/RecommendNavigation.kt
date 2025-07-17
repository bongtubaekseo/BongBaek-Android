package com.bongtu.baekseo.presentation.recommend.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.core.util.sharedViewModel
import com.bongtu.baekseo.presentation.recommend.RecommendFinalRoute
import com.bongtu.baekseo.presentation.recommend.RecommendIntroScreen
import com.bongtu.baekseo.presentation.recommend.RecommendLoadingRoute
import com.bongtu.baekseo.presentation.recommend.RecommendMainRoute
import com.bongtu.baekseo.presentation.recommend.RecommendResultRoute
import com.bongtu.baekseo.presentation.recommend.RecommendViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToRecommend(navOptions: NavOptions? = null) =
    navigate(Recommend, navOptions)

fun NavController.navigateToRecommendMain(navOptions: NavOptions? = null) =
    navigate(RecommendMain, navOptions)

fun NavController.navigateToRecommendLoading(navOptions: NavOptions? = null) =
    navigate(RecommendLoading, navOptions)

fun NavController.navigateToRecommendResult(navOptions: NavOptions? = null) =
    navigate(RecommendResult, navOptions)

fun NavHostController.navigateBackToRecommendMain() = popBackStack(RecommendMain, inclusive = false)

fun NavHostController.navigateToRecommendFinal(navOptions: NavOptions? = null) =
    navigate(RecommendFinal, navOptions)

fun NavGraphBuilder.recommendGraph(
    navController: NavHostController,
    navigateToUp: () -> Unit,
    navigateBackToMain: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToRecord: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<Recommend>(
        startDestination = RecommendIntro,
    ) {
        composable<RecommendIntro> {
            RecommendIntroScreen(
                navigateToUp = navigateToUp,
                navigateToMain = navController::navigateToRecommendMain,
                modifier = modifier,
            )
        }

        composable<RecommendMain> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<RecommendViewModel>(navController)

            RecommendMainRoute(
                navigateToUp = navController::navigateUp,
                navigateToLoading = navController::navigateToRecommendLoading,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<RecommendLoading> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<RecommendViewModel>(navController)

            RecommendLoadingRoute(
                navigateToResult = navController::navigateToRecommendResult,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<RecommendResult> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<RecommendViewModel>(navController)

            RecommendResultRoute(
                navigateToFinal = navController::navigateToRecommendFinal,
                navigateToEdit = navigateToEdit,
                navigateBackToMain = navigateBackToMain,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<RecommendFinal> {
            RecommendFinalRoute(
                navigateToHome = navigateToHome,
                navigateToRecord = navigateToRecord,
                modifier = modifier,
            )
        }
    }
}

@Serializable
data object Recommend : MainTabRoute

@Serializable
data object RecommendIntro : Route

@Serializable
data object RecommendMain : Route

@Serializable
data object RecommendLoading : Route

@Serializable
data object RecommendResult : Route

@Serializable
data object RecommendFinal : Route
