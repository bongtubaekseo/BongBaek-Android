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
import kotlinx.serialization.Serializable

fun NavController.navigateToEdit(navOptions: NavOptions? = null) =
    navigate(Edit, navOptions)

fun NavGraphBuilder.editGraph(
    navController: NavController,
    navigateToUp: () -> Unit,
    navigateToFinal: () -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToRecord: () -> Unit,
    navigateToSchedule: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Edit> { backStackEntry ->
        val previousDestination = navController.previousBackStackEntry?.destination

        val entryType: EditEntryType = when {
            previousDestination?.hasRoute(Detail::class) == true -> {
                EditEntryType.FROM_DETAIL
            }
//            previousDestination?.hasRoute(Result::class) == true -> {         // TODO: 중첩 네비 분리 후 활성화
//                EditEntryType.FROM_RESULT
//            }
//            previousDestination?.hasRoute(Schedule::class) == true -> {      // TODO: 중첩 네비 분리 후 활성화
//                EditEntryType.FROM_SCHEDULE
//            }
            else -> {
                EditEntryType.FROM_RECORD
            }
        }

        val navigateComplete = when (entryType) {
            EditEntryType.FROM_RECORD -> navigateToRecord
            EditEntryType.FROM_SCHEDULE -> navigateToSchedule
            EditEntryType.FROM_DETAIL -> {
                { navigateToDetail("") }                // TODO: Caching 데이터 사용
            }

            EditEntryType.FROM_RESULT -> navigateToFinal
        }

        EditRoute(
            editEntryType = entryType,
            navigateUp = navigateToUp,
            navigateComplete = navigateComplete,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.nestedEditGraph(
    editEntryType: EditEntryType,
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
            editEntryType = editEntryType,
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