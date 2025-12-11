package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.content.ContentsDto
import com.bongtu.baekseo.data.dto.content.GetContentsByPageResponse
import com.bongtu.baekseo.data.dto.content.GetContentsDetailResponse
import com.bongtu.baekseo.data.model.content.Contents
import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.data.model.content.PagedContents
import kotlinx.collections.immutable.toImmutableList

fun ContentsDto.toModel() = Contents(
    contentId = contentId,
    contentTitle = contentTitle,
    contentCategory = contentCategory,
    thumbnailUrl = thumbnailUrl,
    createdAt = createdAt,
)

fun GetContentsByPageResponse.toModel() = PagedContents(
    contents = contents.map { it.toModel() }.toImmutableList(),
    totalContentsCount = totalElements,
    currentPage = currentPage,
    isLast = isLast,
)

fun GetContentsDetailResponse.toModel() = ContentsDetail(
    contentId = contentId,
    contentTitle = contentTitle,
    contentCategory = contentCategory,
    imageUrls = imageUrls,
    createdAt = createdAt,
)
