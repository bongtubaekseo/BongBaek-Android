package com.bongtu.baekseo.presentation.detail.type

sealed interface DetailDropDownTrailingType {
    data class TrailingText(val text: String) : DetailDropDownTrailingType
    data class TrailingChip(val text: String) : DetailDropDownTrailingType
}