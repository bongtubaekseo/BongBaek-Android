package com.bongtu.baekseo.presentation.recommend.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RecommendRoute {
    @Serializable
    data object Intro : RecommendRoute

    @Serializable
    data object Main : RecommendRoute

    @Serializable
    data object Result : RecommendRoute

    @Serializable
    data object Finish : RecommendRoute
}
