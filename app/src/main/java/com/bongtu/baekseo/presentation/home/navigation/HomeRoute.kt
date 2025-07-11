package com.bongtu.baekseo.presentation.home.navigation

import com.bongtu.baekseo.core.common.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable
    data object Main: HomeRoute
    @Serializable
    data object Schedule: HomeRoute
}