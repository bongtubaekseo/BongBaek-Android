package com.bongtu.baekseo.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.bongtu.baekseo.presentation.contents.navigation.navigateToContents
import com.bongtu.baekseo.presentation.home.navigation.navigateToHome
import com.bongtu.baekseo.presentation.recommend.navigation.navigateToRecommend
import com.bongtu.baekseo.presentation.record.navigation.navigateToRecord
import com.bongtu.baekseo.presentation.setting.navigation.SettingMain
import com.bongtu.baekseo.presentation.setting.navigation.navigateToSetting

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    var isBottomBarVisible: Boolean by mutableStateOf(true)
        private set

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    saveState = true
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions = navOptions)
            MainTab.RECOMMEND -> navController.navigateToRecommend()
            MainTab.RECORD -> navController.navigateToRecord(navOptions = navOptions)
            MainTab.CONTENTS -> navController.navigateToContents(navOptions = navOptions)
            MainTab.SETTING -> navController.navigateToSetting(navOptions = navOptions)
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    @Composable
    fun showBottomBar(): Boolean {
        val isMainTabRoute = MainTab.contains {
            currentDestination?.hasRoute(it::class) == true
        }

        val tab = MainTab.find { currentDestination?.hasRoute(it::class) == true }

        return (isMainTabRoute && isBottomBarVisible) && tab != MainTab.RECOMMEND
    }

    fun updateBottomBarVisible(isVisible: Boolean) {
        isBottomBarVisible = isVisible
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
