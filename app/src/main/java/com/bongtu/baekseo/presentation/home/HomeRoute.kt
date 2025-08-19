package com.bongtu.baekseo.presentation.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Main
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Schedule
import com.bongtu.baekseo.presentation.schedule.ScheduleRoute
import com.bongtu.baekseo.presentation.schedule.navigation.navigateToSchedule

@Composable
fun HomeRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToDetail: (String) -> Unit,
    bottomPadding: Dp,
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
    ) {
        composable<Main> {
            HomeMainRoute(
                navigateToEdit = navigateToEdit,
                navigateToRecommend = navigateToRecommend,
                navigateToSchedule = navController::navigateToSchedule,
                modifier = modifier
                    .padding(bottom = bottomPadding),
            )
        }

        composable<Schedule> {
            ScheduleRoute(
                setBottomBarVisible = setBottomBarVisible,
                onBackClick = navController::navigateUp,
                navigateToDetail = navigateToDetail,
                navigateToEdit = navigateToEdit,
                modifier = modifier,
            )
        }
    }
}
