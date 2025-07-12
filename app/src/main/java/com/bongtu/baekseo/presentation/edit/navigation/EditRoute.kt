package com.bongtu.baekseo.presentation.edit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface EditRoute {
    @Serializable
    data object Main: EditRoute
    @Serializable
    data object Location : EditRoute
}