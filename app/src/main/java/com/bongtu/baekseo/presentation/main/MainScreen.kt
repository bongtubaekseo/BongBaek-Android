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
import com.bongtu.baekseo.presentation.contents.navigation.contentsGraph
import com.bongtu.baekseo.presentation.contents.navigation.navigateToContents
import com.bongtu.baekseo.presentation.detail.navigation.detailGraph
import com.bongtu.baekseo.presentation.detail.navigation.navigateToDetail
import com.bongtu.baekseo.presentation.edit.navigation.Edit
import com.bongtu.baekseo.presentation.edit.navigation.editGraph
import com.bongtu.baekseo.presentation.edit.navigation.navigateToEdit
import com.bongtu.baekseo.presentation.home.navigation.homeGraph
import com.bongtu.baekseo.presentation.home.navigation.navigateToHome
import com.bongtu.baekseo.presentation.login.navigation.Login
import com.bongtu.baekseo.presentation.login.navigation.loginGraph
import com.bongtu.baekseo.presentation.login.navigation.navigateToLogin
import com.bongtu.baekseo.presentation.main.component.MainBottomBar
import com.bongtu.baekseo.presentation.onboarding.navigation.navigateToOnBoarding
import com.bongtu.baekseo.presentation.onboarding.navigation.onBoardingGraph
import com.bongtu.baekseo.presentation.profileedit.navigation.navigateToProfileEdit
import com.bongtu.baekseo.presentation.profileedit.navigation.profileEditGraph
import com.bongtu.baekseo.presentation.recommend.navigation.Recommend
import com.bongtu.baekseo.presentation.recommend.navigation.navigateBackToRecommendMain
import com.bongtu.baekseo.presentation.recommend.navigation.navigateToRecommend
import com.bongtu.baekseo.presentation.recommend.navigation.navigateToRecommendFinal
import com.bongtu.baekseo.presentation.recommend.navigation.recommendGraph
import com.bongtu.baekseo.presentation.record.navigation.navigateToRecord
import com.bongtu.baekseo.presentation.record.navigation.recordGraph
import com.bongtu.baekseo.presentation.setting.navigation.settingGraph
import com.bongtu.baekseo.presentation.splash.navigation.Splash
import com.bongtu.baekseo.presentation.splash.navigation.splashGraph
import com.bongtu.baekseo.presentation.withdraw.navigation.navigateToWithdraw
import com.bongtu.baekseo.presentation.withdraw.navigation.withdrawGraph
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainScreen(
    isStartLogin: Boolean,
    onRestartApp: (Boolean) -> Unit,
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
        containerColor = BongBaekTheme.colors.bgDisplayPrimary,
        modifier = Modifier
            .fillMaxSize(),
    ) { innerPadding ->
        CompositionLocalProvider(LocalBottomNavigationBarsPadding provides innerPadding) {
            MainNavHost(
                isStartLogin = isStartLogin,
                onRestartApp = onRestartApp,
                navigator = navigator,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun MainNavHost(
    isStartLogin: Boolean,
    onRestartApp: (Boolean) -> Unit,
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    val startDestination = if (isStartLogin) Login else Splash

    NavHost(
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
        navController = navigator.navController,
        startDestination = startDestination,
    ) {
        splashGraph(
            onRestartApp = onRestartApp,
            navigateToLogin = {
                navigator.navController.navigateToLogin(
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

        loginGraph(
            navigateToOnBoarding = navigator.navController::navigateToOnBoarding,
            navigateToHome = {
                navigator.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Login> {
                            inclusive = true
                        }
                    }
                )
            },
            modifier = modifier,
        )

        onBoardingGraph(
            navigateToUp = navigator::navigateUp,
            navigateToHome = {
                navigator.navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Login> {
                            inclusive = true
                        }
                    }
                )
            },
            modifier = modifier,
        )

        homeGraph(
            navController = navigator.navController,
            navigateToRecord = navigator.navController::navigateToRecord,
            navigateToRecommend = navigator.navController::navigateToRecommend,
            navigateToContents = navigator.navController::navigateToContents,
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
            navigateToDetail = navigator.navController::navigateToDetail,
            navigateToAdd = navigator.navController::navigateToEdit,
            modifier = modifier,
        )

        contentsGraph(
            navController = navigator.navController,
            navigateToUp = navigator::navigateUp,
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

        settingGraph(
            navigateToProfileEdit = navigator.navController::navigateToProfileEdit,
            navigateToWithdraw = navigator.navController::navigateToWithdraw,
            onRestartApp = onRestartApp,
            modifier = modifier,
        )

        withdrawGraph(
            navigateToUp = navigator::navigateUp,
            onRestartApp = onRestartApp,
            modifier = modifier,
        )

        profileEditGraph(
            navigateToUp = navigator::navigateUp,
            modifier = modifier,
        )
    }
}
