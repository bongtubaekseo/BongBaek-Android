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
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
import com.bongtu.baekseo.presentation.recommend.navigation.RecommendResult
import com.bongtu.baekseo.presentation.record.navigation.Record
import kotlinx.serialization.Serializable

fun NavController.navigateToEdit(navOptions: NavOptions? = null) = navigate(Edit, navOptions)

fun NavGraphBuilder.editGraph(
    navController: NavController,
    navigateToUp: () -> Unit,
    navigateToFinal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Edit> { backStackEntry ->
        val previousDestination = navController.previousBackStackEntry?.destination

        val entryType: EditEntryType = when {
            previousDestination?.hasRoute(Detail::class) == true -> {
                EditEntryType.FROM_DETAIL
            }

            previousDestination?.hasRoute(RecommendResult::class) == true -> {
                EditEntryType.FROM_RESULT
            }

            previousDestination?.hasRoute(Record::class) == true -> {
                EditEntryType.FROM_RECORD
            }

            else -> {
                EditEntryType.FROM_SCHEDULE
            }
        }

        EditRoute(
            editEntryType = entryType,
            navigateUp = navigateToUp,
            navigateToFinal = navigateToFinal,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.nestedEditGraph(
    editEntryType: EditEntryType,
    navigateUp: () -> Unit,
    nestedNavigateUp: () -> Unit,
    navigateToFinal: () -> Unit,
    navigateToLocation: () -> Unit,
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
) {
    composable<Main> {
        EditMainRoute(
            editEntryType = editEntryType,
            navigateUp = navigateUp,
            navigateToFinal = navigateToFinal,
            navigateToLocation = navigateToLocation,
            viewModel = viewModel,
            modifier = modifier,
        )
    }

    composable<Location> {
        EditLocationRoute(
            navigateUp = nestedNavigateUp,
            viewModel = viewModel,
            modifier = modifier,
        )
    }
}

@Serializable
data object Edit : Route