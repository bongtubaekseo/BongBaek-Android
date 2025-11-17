package com.bongtu.baekseo.presentation.login.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.login.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(Login, navOptions)

fun NavGraphBuilder.loginGraph(
    navigateToOnBoarding: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Login> {
        LoginRoute(
            navigateToOnBoarding = navigateToOnBoarding,
            navigateToHome = navigateToHome,
            modifier = modifier,
        )
    }
}

@Serializable
data object Login : Route