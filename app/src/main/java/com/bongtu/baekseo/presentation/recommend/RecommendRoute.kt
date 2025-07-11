package com.bongtu.baekseo.presentation.recommend

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bongtu.baekseo.presentation.recommend.navigation.RecommendRoute
import com.bongtu.baekseo.presentation.recommend.navigation.nestedRecommendGraph

@Composable
fun RecommendRoute(
    navigateToUp: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecommendViewModel = hiltViewModel(),
) {
    val recommendNavigator: NavHostController = rememberNavController()

    NavHost(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        navController = recommendNavigator,
        startDestination = RecommendRoute.Intro,
    ) {
        nestedRecommendGraph(
            navController = recommendNavigator,
            navigateToUp = navigateToUp,
            navigateToEdit = navigateToEdit,
            navigateToHome = navigateToHome,
            navigateToRecord = navigateToRecord,
            viewModel = viewModel,
            modifier = modifier,
        )
    }
}
