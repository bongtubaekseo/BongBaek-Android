package com.bongtu.baekseo.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.detail.navigation.Detail
import com.bongtu.baekseo.presentation.detail.navigation.detailGraph
import com.bongtu.baekseo.presentation.detail.navigation.navigateToDetail
import com.bongtu.baekseo.presentation.dummy.navigation.dummyGraph
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
import com.bongtu.baekseo.presentation.recommend.navigation.navigateToRecommend
import com.bongtu.baekseo.presentation.recommend.navigation.recommendGraph
import com.bongtu.baekseo.presentation.record.navigation.navigateToRecord
import com.bongtu.baekseo.presentation.record.navigation.recordGraph
import com.bongtu.baekseo.presentation.splash.navigation.Splash
import com.bongtu.baekseo.presentation.splash.navigation.splashGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                tabs = MainTab.entries.toImmutableList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate,
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(BongBaekTheme.colors.gray900),
    ) { innerPadding ->
        MainNavHost(
            navigator = navigator,
            innerPadding = innerPadding,
            modifier = Modifier,
        )
    }
}

@Composable
private fun MainNavHost(
    navigator: MainNavigator,
    innerPadding: PaddingValues,
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
        dummyGraph(modifier = modifier)

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
            setBottomBarVisible = navigator::updateBottomBarVisible,
            navigateToRecord = {
                navigator.navController.navigateToRecord()
            },
            navigateToRecommend = {
                navigator.navController.navigateToRecommend()
            },
            modifier = modifier.padding(bottom = innerPadding.calculateBottomPadding()),
        )

        recommendGraph(
            navigateToUp = navigator::navigateUp,
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
            navigateToEdit = navigator.navController::navigateToEdit, // TODO: Caching
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
            innerPadding = innerPadding,
            bottomPadding = innerPadding.calculateBottomPadding(),
            modifier = modifier,
        )

        detailGraph(
            navigateUp = navigator::navigateUp,
            navigateToEdit = navigator.navController::navigateToEdit,  // TODO: Caching
            navigateToRecord = {
                navigator.navController.navigateToRecord(
                    navOptions = navOptions {
                        popUpTo<Detail> {
                            inclusive = true
                        }
                    }
                )
            },
            modifier = modifier,
        )

        editGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
            navigateToFinal = navigator::navigateUp,        // TODO: 네비 방식 점검 필요
            navigateToDetail = { eventId ->
                navigator.navController.navigateToDetail(
                    eventId = eventId,
                    navOptions = navOptions {
                        popUpTo<Edit> {
                            inclusive = true
                        }
                    },
                )
            },
            navigateToRecord = {
                navigator.navController.navigateToRecord(
                    navOptions = navOptions {
                        popUpTo<Edit> {
                            inclusive = true
                        }
                    }
                )
            },
            modifier = modifier,
        )
    }
}
