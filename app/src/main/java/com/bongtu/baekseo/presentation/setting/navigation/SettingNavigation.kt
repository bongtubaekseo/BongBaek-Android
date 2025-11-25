package com.bongtu.baekseo.presentation.setting.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.core.util.sharedViewModel
import com.bongtu.baekseo.presentation.setting.ProfileEditRoute
import com.bongtu.baekseo.presentation.setting.SettingRoute
import com.bongtu.baekseo.presentation.setting.SettingViewModel
import kotlinx.serialization.Serializable

fun NavController.navigateToSetting(navOptions: NavOptions? = null) =
    navigate(Setting, navOptions)

fun NavController.navigateToProfileEdit(navOptions: NavOptions? = null) =
    navigate(ProfileEdit, navOptions)

fun NavGraphBuilder.settingGraph(
    navController: NavHostController,
    navigateToWithdraw: () -> Unit,
    onRestartApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<Setting>(
        startDestination = SettingMain,
    ) {
        composable<SettingMain> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SettingViewModel>(navController)

            SettingRoute(
                navigateToEditProfile = navController::navigateToProfileEdit,
                navigateToWithdraw = navigateToWithdraw,
                onRestartApp = onRestartApp,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<ProfileEdit> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<SettingViewModel>(navController)

            ProfileEditRoute(
                navigateToUp = navController::navigateUp,
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}

@Serializable
data object Setting : MainTabRoute

@Serializable
data object SettingMain : Route

@Serializable
data object ProfileEdit : Route
