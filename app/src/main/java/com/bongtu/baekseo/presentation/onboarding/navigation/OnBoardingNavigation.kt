package com.bongtu.baekseo.presentation.onboarding.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.onboarding.OnBoardingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToOnBoarding(navOptions: NavOptions? = null) =
    navigate(OnBoarding, navOptions)

fun NavGraphBuilder.onBoardingGraph(
    modifier: Modifier = Modifier,
) {
    composable<OnBoarding> {
        OnBoardingRoute(
            modifier = modifier,
        )
    }
}

@Serializable
data object OnBoarding : Route