package com.bongtu.baekseo.presentation.home.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable
    data object Main: HomeRoute
    @Serializable
    data object Schedule: HomeRoute
}