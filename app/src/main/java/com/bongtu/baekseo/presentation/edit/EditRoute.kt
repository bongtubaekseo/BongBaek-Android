package com.bongtu.baekseo.presentation.edit

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bongtu.baekseo.presentation.edit.navigation.EditRoute.Main
import com.bongtu.baekseo.presentation.edit.navigation.nestedEditGraph
import com.bongtu.baekseo.presentation.edit.type.EditType

@Composable
fun EditRoute(
    navigateUp: () -> Unit,
    editType: EditType,
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToFinal: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Main,
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
            navigateToFinal = navigateToFinal,
            navigateToRecord = navigateToRecord,
            viewModel = viewModel,
            modifier = modifier,
        )
    }
}