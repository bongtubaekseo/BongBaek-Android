package com.bongtu.baekseo.data.model.content

import kotlinx.collections.immutable.ImmutableList

data class Contents(
    val contentId: String,
    val contentTitle: String,
    val contentCategory: String,
    val thumbnailUrl: String,
    val createdAt: String? = null,
)

data class PagedContents(
    val contents: ImmutableList<Contents>,
    val currentPage: Int,
    val isLast: Boolean,
)
