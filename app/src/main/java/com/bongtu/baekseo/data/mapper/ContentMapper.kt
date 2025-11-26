package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.content.ContentsDto
import com.bongtu.baekseo.data.dto.content.GetContentsDetailResponse
import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.data.model.content.HomeContent

fun ContentsDto.toModel() = HomeContent(
    contentId = contentId,
    contentTitle = contentTitle,
    contentCategory = contentCategory,
    thumbnailUrl = thumbnailUrl,
    createdAt = createdAt,
)

fun GetContentsDetailResponse.toModel() = ContentsDetail(
    contentId = contentId,
    contentTitle = contentTitle,
    contentCategory = contentCategory,
    imageUrls = imageUrls,
    createdAt = createdAt,
)
