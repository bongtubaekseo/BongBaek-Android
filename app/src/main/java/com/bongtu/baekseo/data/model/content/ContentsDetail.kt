package com.bongtu.baekseo.data.model.content

data class ContentsDetail(
    val contentId: String,
    val contentTitle: String,
    val contentCategory: String,
    val imageUrls: List<String>,
    val createdAt: String,
)
