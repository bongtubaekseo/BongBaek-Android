package com.bongtu.baekseo.presentation.edit

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bongtu.baekseo.presentation.edit.navigation.EditRoute
import com.bongtu.baekseo.presentation.edit.navigation.nestedEditGraph
import com.bongtu.baekseo.presentation.edit.type.EditType

@Composable
fun EditRoute(
    navigateUp: () -> Unit,
    editType: EditType,
    navigateComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EditRoute.Main,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        modifier = modifier
            .fillMaxSize(),
    ) {
        nestedEditGraph(
            editType = editType,
            navigateUp = navigateUp,
            nestedNavigateUp = navController::navigateUp,
            navigateComplete = navigateComplete,
            viewModel = viewModel,
            navigateToMain = {
                navController.navigate(
                    route = EditRoute.Main,
                    navOptions = navOptions {
                        popUpTo<EditRoute.Main> {
                            inclusive = false
                        }
                        launchSingleTop = true
                    },
                )
            },
            navigateToLocation = { navController.navigate(EditRoute.Location) },
            modifier = modifier,
        )
    }
}