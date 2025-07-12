package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostEventCostRequest(
    @SerialName("category")
    val category: String,
    @SerialName("relationship")
    val relationship: String,
    @SerialName("attended")
    val attended: Boolean,
    @SerialName("locationInfo")
    val locationInfo: LocationInfoDto,
    @SerialName("HighAccuracy")
    val highAccuracy: HighAccuracyDto,
)
