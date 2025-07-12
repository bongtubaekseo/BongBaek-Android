package com.bongtu.baekseo.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostEventCostResponse(
    @SerialName("cost")
    val cost: Int,
    @SerialName("range")
    val range: Range,
    @SerialName("category")
    val category: String,
    @SerialName("location")
    val location: String,
    @SerialName("params")
    val params: Params,
) {
    @Serializable
    data class Range(
        @SerialName("min")
        val min: Int,
        @SerialName("max")
        val max: Int,
    )

    @Serializable
    data class Params(
        @SerialName("age")
        val age: Int,
        @SerialName("income")
        val income: String,
        @SerialName("category")
        val category: String,
        @SerialName("relationship")
        val relationship: Boolean,
        @SerialName("attended")
        val attended: Boolean,
        @SerialName("contactFrequency")
        val contactFrequency: Int,
        @SerialName("meetFrequency")
        val meetFrequency: Int,
    )
}
