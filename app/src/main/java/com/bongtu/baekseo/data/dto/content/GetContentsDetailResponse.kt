package com.bongtu.baekseo.data.dto.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetContentsDetailResponse(
    @SerialName("contentId")
    val contentId: String,
    @SerialName("contentTitle")
    val contentTitle: String,
    @SerialName("contentCategory")
    val contentCategory: String,
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("createdAt")
    val createdAt: String,
)
