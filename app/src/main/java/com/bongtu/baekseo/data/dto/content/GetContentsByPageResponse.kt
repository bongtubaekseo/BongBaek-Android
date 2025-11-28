package com.bongtu.baekseo.data.dto.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetContentsByPageResponse(
    @SerialName("contents")
    val contents: List<ContentsDto>,
    @SerialName("currentPage")
    val currentPage: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("totalElements")
    val totalElements: Int,
    @SerialName("isLast")
    val isLast: Boolean,
)
