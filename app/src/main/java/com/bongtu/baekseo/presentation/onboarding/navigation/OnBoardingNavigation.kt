package com.bongtu.baekseo.presentation.onboarding.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.onboarding.OnBoardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToOnBoarding(oauthId: String, navOptions: NavOptions? = null) =
    navigate(OnBoarding(oauthId), navOptions)

fun NavGraphBuilder.onBoardingGraph(
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<OnBoarding> {
        OnBoardingRoute(
            navigateToUp = navigateToUp,
            navigateToHome = navigateToHome,
            modifier = modifier,
        )
    }
}

@Serializable
data class OnBoarding(
    val oauthId: String,
) : Route