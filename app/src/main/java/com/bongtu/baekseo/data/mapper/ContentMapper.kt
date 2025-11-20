package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.content.GetHomeContentsResponse
import com.bongtu.baekseo.data.model.content.HomeContent

fun GetHomeContentsResponse.Content.toModel() = HomeContent(
    contentId = contentId,
    contentTitle = contentTitle,
    contentCategory = contentCategory,
    thumbnailUrl = thumbnailUrl,
)