package com.bongtu.baekseo.presentation.edit.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.detail.navigation.Detail
import com.bongtu.baekseo.presentation.edit.EditLocationRoute
import com.bongtu.baekseo.presentation.edit.EditMainRoute
import com.bongtu.baekseo.presentation.edit.EditRoute
import com.bongtu.baekseo.presentation.edit.EditViewModel
import com.bongtu.baekseo.presentation.edit.navigation.EditRoute.Location
import com.bongtu.baekseo.presentation.edit.navigation.EditRoute.Main
import com.bongtu.baekseo.presentation.edit.type.EditType
import com.bongtu.baekseo.presentation.recommend.navigation.Recommend
import kotlinx.serialization.Serializable

fun NavController.navigateToEdit(navOptions: NavOptions? = null) =
    navigate(Edit, navOptions)

fun NavGraphBuilder.editGraph(
    navController: NavController,
    navigateToUp: () -> Unit,
    navigateToFinal: () -> Unit,
    navigateToDetail: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Edit> { backStackEntry ->
        val previousDestination = navController.previousBackStackEntry?.destination

        val (editType, navigateComplete) = when {
            previousDestination?.hasRoute(Detail::class) == true ->
                EditType.EDIT to navigateToDetail

            previousDestination?.hasRoute(Recommend::class) == true ->
                EditType.EDIT to navigateToFinal

            else ->
                EditType.ADD to navigateToRecord
        }

        EditRoute(
            navigateUp = navigateToUp,
            editType = editType,
            navigateComplete = navigateComplete,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.nestedEditGraph(
    editType: EditType,
    navigateUp: () -> Unit,
    nestedNavigateUp: () -> Unit,
    navigateComplete: () -> Unit,
    navigateToLocation: () -> Unit,
    navigateToMain: () -> Unit,
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
) {
    composable<Main> {
        EditMainRoute(
            editType = editType,
            navigateUp = navigateUp,
            navigateComplete = navigateComplete,
            navigateToLocation = navigateToLocation,
            viewModel = viewModel,
            modifier = modifier,
        )
    }

    composable<Location> {
        EditLocationRoute(
            navigateUp = nestedNavigateUp,
            navigateToEdit = navigateToMain,
            viewModel = viewModel,
            modifier = modifier,
        )
    }
}

@Serializable
data object Edit : Route