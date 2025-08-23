package com.bongtu.baekseo.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bongtu.baekseo.core.compositionlocal.LocalBottomNavigationBarsPadding
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.detail.navigation.detailGraph
import com.bongtu.baekseo.presentation.detail.navigation.navigateToDetail
import com.bongtu.baekseo.presentation.edit.navigation.Edit
import com.bongtu.baekseo.presentation.edit.navigation.editGraph
import com.bongtu.baekseo.presentation.edit.navigation.navigateToEdit
import com.bongtu.baekseo.presentation.home.navigation.homeGraph
import com.bongtu.baekseo.presentation.home.navigation.navigateToHome
import com.bongtu.baekseo.presentation.main.component.MainBottomBar
import com.bongtu.baekseo.presentation.onboarding.navigation.OnBoarding
import com.bongtu.baekseo.presentation.onboarding.navigation.navigateToOnBoarding
import com.bongtu.baekseo.presentation.onboarding.navigation.onBoardingGraph
import com.bongtu.baekseo.presentation.recommend.navigation.Recommend
import com.bongtu.baekseo.presentation.recommend.navigation.navigateBackToRecommendMain
import com.bongtu.baekseo.presentation.recommend.navigation.navigateToRecommend
import com.bongtu.baekseo.presentation.recommend.navigation.navigateToRecommendFinal
import com.bongtu.baekseo.presentation.recommend.navigation.recommendGraph
import com.bongtu.baekseo.presentation.record.navigation.navigateToRecord
import com.bongtu.baekseo.presentation.record.navigation.recordGraph
import com.bongtu.baekseo.presentation.schedule.navigation.navigateToSchedule
import com.bongtu.baekseo.presentation.schedule.navigation.scheduleGraph
import com.bongtu.baekseo.presentation.splash.navigation.Splash
import com.bongtu.baekseo.presentation.splash.navigation.splashGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = navigator.showBottomBar(),
                enter = fadeIn() + slideIn { IntOffset(0, it.height) },
                exit = fadeOut() + slideOut { IntOffset(0, it.height) },
            ) {
                MainBottomBar(
                    tabs = MainTab.entries.toImmutableList(),
                    currentTab = navigator.currentTab,
                    onTabSelected = navigator::navigate,
                )
            }
        },
        containerColor = BongBaekTheme.colors.gray900,
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        CompositionLocalProvider(LocalBottomNavigationBarsPadding provides innerPadding) {
            MainNavHost(
                navigator = navigator,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun MainNavHost(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    NavHost(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        navController = navigator.navController,
        startDestination = navigator.startDestination,
    ) {
        splashGraph(
            navigateToOnBoarding = {
                navigator.navController.navigateToOnBoarding(
                    navOptions = navOptions {
                        popUpTo<Splash> {
                            inclusive = true
                        }
                    }
                )
            },
            navigateToHome = {
                navigator.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Splash> {
                            inclusive = true
                        }
                    }
                )
            },
            modifier = modifier,
        )

        onBoardingGraph(
            navigateToHome = {
                navigator.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<OnBoarding> {
                            inclusive = true
                        }
                    }
                )
            },
            modifier = modifier,
        )

        homeGraph(
            navigateToRecommend = navigator.navController::navigateToRecommend,
            navigateToEdit = navigator.navController::navigateToEdit,
            navigateToSchedule = navigator.navController::navigateToSchedule,
            modifier = modifier,
        )

        recommendGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
            navigateBackToMain = navigator.navController::navigateBackToRecommendMain,
            navigateToHome = {
                navigator.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Recommend> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            navigateToRecord = {
                navigator.navController.navigateToRecord(
                    navOptions = navOptions {
                        popUpTo<Recommend> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                )
            },
            navigateToEdit = navigator.navController::navigateToEdit,
            modifier = modifier,
        )

        recordGraph(
            setBottomBarVisible = navigator::updateBottomBarVisible,
            navigateToDetail = { eventId ->
                navigator.navController.navigateToDetail(
                    eventId = eventId,
                )
            },
            navigateToAdd = navigator.navController::navigateToEdit,
            modifier = modifier,
        )

        detailGraph(
            navigateUp = navigator::navigateUp,
            navigateToEdit = navigator.navController::navigateToEdit,
            modifier = modifier,
        )

        editGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
            navigateToFinal = {
                navigator.navController.navigateToRecommendFinal(
                    navOptions = navOptions {
                        popUpTo<Edit> {
                            inclusive = true
                        }
                    },
                )
            },
            modifier = modifier,
        )

        scheduleGraph(
            navigateToUp = navigator::navigateUp,
            navigateToEdit = navigator.navController::navigateToEdit,
            navigateToDetail = { eventId ->
                navigator.navController.navigateToDetail(
                    eventId = eventId,
                )
            },
            modifier = modifier,
        )
    }
}
