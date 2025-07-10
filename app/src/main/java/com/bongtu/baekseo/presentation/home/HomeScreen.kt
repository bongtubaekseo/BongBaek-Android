package com.bongtu.baekseo.presentation.home

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute
import com.bongtu.baekseo.presentation.home.navigation.homeDefaultGraph
import com.bongtu.baekseo.presentation.home.schedule.navigation.navigateToSchedule
import com.bongtu.baekseo.presentation.home.schedule.navigation.scheduleGraph

@Composable
fun HomeRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeScreen(
        setBottomBarVisible = setBottomBarVisible,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    setBottomBarVisible: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute.Default,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        modifier = modifier
            .fillMaxSize(),
    ) {
        homeDefaultGraph(
            navigateToSchedule = navController::navigateToSchedule,
            modifier = Modifier,
        )
        scheduleGraph(
            setBottomBarVisible = setBottomBarVisible,
            onBackClick = navController::popBackStack,
            modifier = Modifier,
        )
    }
}
