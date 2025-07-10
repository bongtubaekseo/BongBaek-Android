package com.bongtu.baekseo.presentation.home.schedule.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Schedule
import com.bongtu.baekseo.presentation.home.schedule.ScheduleRoute

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) =
    navigate(Schedule, navOptions)

fun NavGraphBuilder.scheduleGraph(
    setBottomBarVisible: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Schedule> {
        ScheduleRoute(
            setBottomBarVisible = setBottomBarVisible,
            onBackClick = onBackClick,
            modifier = modifier,
        )
    }
}