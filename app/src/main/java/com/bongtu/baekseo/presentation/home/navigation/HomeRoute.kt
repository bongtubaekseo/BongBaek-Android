package com.bongtu.baekseo.presentation.home.navigation

import com.bongtu.baekseo.core.common.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute: Route {
    @Serializable
    data object Default: HomeRoute
    @Serializable
    data object Schedule: HomeRoute
}