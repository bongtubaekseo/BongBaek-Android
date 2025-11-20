package com.bongtu.baekseo.data.dto.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHomeContentsResponse(
    val contents: List<Content>,
) {
    @Serializable
    data class Content(
        @SerialName("contentId")
        val contentId: String,
        @SerialName("contentTitle")
        val contentTitle: String,
        @SerialName("contentCategory")
        val contentCategory: String,
        @SerialName("thumbnailUrl")
        val thumbnailUrl: String,
    )
}