package com.bongtu.baekseo.presentation.setting.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.presentation.setting.SettingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSetting(navOptions: NavOptions? = null) =
    navigate(Setting, navOptions)

fun NavGraphBuilder.settingGraph(
    navigateToProfileEdit: (String, String, IncomeType) -> Unit,
    navigateToWithdraw: () -> Unit,
    onRestartApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Setting> {
        SettingRoute(
            navigateToEditProfile = navigateToProfileEdit,
            navigateToWithdraw = navigateToWithdraw,
            onRestartApp = onRestartApp,
            modifier = modifier,
        )
    }
}

@Serializable
data object Setting : MainTabRoute
