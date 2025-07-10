package com.bongtu.baekseo.presentation.record.type

sealed interface RecordDetailDropDownTrailingType {
    data class TrailingText(val text: String) : RecordDetailDropDownTrailingType
    data class TrailingChip(val text: String) : RecordDetailDropDownTrailingType
}