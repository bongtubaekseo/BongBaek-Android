package com.bongtu.baekseo.data.dto.map

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoMapResponse(
    @SerialName("documents")
    val documents: List<PlaceDocument>,
    @SerialName("meta")
    val meta: Meta,
) {
    @Serializable
    data class PlaceDocument(
        @SerialName("id")
        val id: String,
        @SerialName("place_name")
        val placeName: String,
        @SerialName("x")
        val x: String,
        @SerialName("y")
        val y: String,
        @SerialName("address_name")
        val addressName: String,
        @SerialName("road_address_name")
        val roadAddress: String,
    )

    @Serializable
    data class Meta(
        @SerialName("total_count")
        val totalCount: Int,
        @SerialName("is_end")
        val isEnd: Boolean,
    )
}