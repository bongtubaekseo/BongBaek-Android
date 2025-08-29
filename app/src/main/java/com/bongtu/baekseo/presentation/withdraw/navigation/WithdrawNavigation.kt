package com.bongtu.baekseo.presentation.withdraw.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.withdraw.WithdrawRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToWithdraw(navOptions: NavOptions? = null) =
    navigate(Withdraw, navOptions)

fun NavGraphBuilder.withdrawGraph(
    modifier: Modifier = Modifier,
) {
    composable<Withdraw> {
        WithdrawRoute(
            modifier = modifier,
        )
    }
}

@Serializable
data object Withdraw : Route
