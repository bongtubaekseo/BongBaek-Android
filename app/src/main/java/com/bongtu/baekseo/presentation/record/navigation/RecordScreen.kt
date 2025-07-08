package com.bongtu.baekseo.presentation.record.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.record.RecordViewModel

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RecordRoute.Default,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        recordNestedGraph(
            navController = navController,
            viewModel = viewModel,
        )
    }
}