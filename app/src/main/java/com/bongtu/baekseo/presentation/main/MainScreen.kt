package com.bongtu.baekseo.presentation.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bongtu.baekseo.presentation.dummy.navigation.dummyGraph
import com.bongtu.baekseo.presentation.home.navigation.homeGraph
import com.bongtu.baekseo.presentation.home.navigation.navigateToHome
import com.bongtu.baekseo.presentation.main.component.MainBottomBar
import com.bongtu.baekseo.presentation.onboarding.navigation.OnBoarding
import com.bongtu.baekseo.presentation.onboarding.navigation.navigateToOnBoarding
import com.bongtu.baekseo.presentation.onboarding.navigation.onBoardingGraph
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
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            MainNavHost(
                navigator = navigator,
                modifier = Modifier.padding(innerPadding),
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
            modifier = modifier,
        )
        recordGraph(
            setBottomBarVisible = navigator::updateBottomBarVisible,
            modifier = modifier,
        )
    }
}