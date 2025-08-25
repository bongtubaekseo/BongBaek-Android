package com.bongtu.baekseo.presentation.edit.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.core.util.sharedViewModel
import com.bongtu.baekseo.data.model.event.EditEvent
import com.bongtu.baekseo.presentation.detail.navigation.Detail
import com.bongtu.baekseo.presentation.edit.EditLocationRoute
import com.bongtu.baekseo.presentation.edit.EditMainRoute
import com.bongtu.baekseo.presentation.edit.EditViewModel
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
import com.bongtu.baekseo.presentation.recommend.navigation.RecommendResult
import com.bongtu.baekseo.presentation.record.navigation.Record
import kotlinx.serialization.Serializable

fun NavController.navigateToEdit(
    editEvent: EditEvent? = null,
    navOptions: NavOptions? = null,
) = navigate(Edit(editEvent), navOptions)

fun NavController.navigateToEditLocation(
    navOptions: NavOptions? = null
) = navigate(EditLocation, navOptions)

fun NavGraphBuilder.editGraph(
    navController: NavHostController,
    navigateToUp: () -> Unit,
    navigateToFinal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<Edit>(
        typeMap = EditNavType.TYPE_MAP,
        startDestination = EditMain,
    ) {
        composable<EditMain> { backStackEntry ->
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

            val viewModel = backStackEntry.sharedViewModel<EditViewModel>(navController)

            EditMainRoute(
                editEntryType = entryType,
                navigateUp = navigateToUp,
                navigateToFinal = navigateToFinal,
                navigateToEditLocation = navController::navigateToEditLocation,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<EditLocation> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<EditViewModel>(navController)

            EditLocationRoute(
                navigateUp = navController::navigateUp,
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}

@Serializable
data class Edit(
    val editEvent: EditEvent?,
) : Route

@Serializable
data object EditMain : Route

@Serializable
data object EditLocation : Route
