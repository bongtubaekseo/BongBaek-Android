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
import com.bongtu.baekseo.presentation.home.schedule.navigation.scheduleGraph

@Composable
fun HomeNavHost(
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
            modifier = Modifier,
        )
        scheduleGraph(
            modifier = Modifier,
        )
    }
}
