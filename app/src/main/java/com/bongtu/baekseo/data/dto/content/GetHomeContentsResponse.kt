package com.bongtu.baekseo.data.dto.content

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHomeContentsResponse(
    @SerialName("contents")
    val contents: List<ContentsDto>,
)
