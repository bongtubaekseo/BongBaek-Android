package com.bongtu.baekseo.presentation.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Main
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Schedule
import com.bongtu.baekseo.presentation.home.schedule.ScheduleRoute
import com.bongtu.baekseo.presentation.home.schedule.navigation.navigateToSchedule

@Composable
fun HomeRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
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
        composable<Main> {
            HomeMainRoute(
                navigateToSchedule = navController::navigateToSchedule,
            )
        }
        composable<Schedule> {
            ScheduleRoute(
                setBottomBarVisible = setBottomBarVisible,
                onBackClick = navController::navigateUp,
            )
        }
    }
}