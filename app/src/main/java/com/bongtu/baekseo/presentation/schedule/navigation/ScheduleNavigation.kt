package com.bongtu.baekseo.presentation.schedule.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.schedule.ScheduleRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) =
    navigate(Schedule, navOptions)

fun NavGraphBuilder.scheduleGraph(
    navigateToUp: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Schedule> {
        ScheduleRoute(
            navigateToUp = navigateToUp,
            navigateToDetail = navigateToDetail,
            navigateToEdit = navigateToEdit,
            modifier = modifier,
        )
    }
}

@Serializable
data object Schedule : Route