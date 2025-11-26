package com.bongtu.baekseo.data.dto.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentsDto(
    @SerialName("contentId")
    val contentId: String,
    @SerialName("contentTitle")
    val contentTitle: String,
    @SerialName("contentCategory")
    val contentCategory: String,
    @SerialName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerialName("createdAt")
    val createdAt: String? = null,
)
