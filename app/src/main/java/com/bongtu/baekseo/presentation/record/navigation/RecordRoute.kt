package com.bongtu.baekseo.presentation.record.navigation

import com.bongtu.baekseo.core.common.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
sealed interface RecordRoute : Route {
    @Serializable
    data object Default : RecordRoute

    @Serializable
    data object Edit : RecordRoute  // TODO: 추가 페이지 예정
}