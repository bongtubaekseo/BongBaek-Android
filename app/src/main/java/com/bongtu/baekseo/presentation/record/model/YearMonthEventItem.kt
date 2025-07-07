package com.bongtu.baekseo.presentation.record.model

import com.bongtu.baekseo.data.model.RecordEvent

sealed interface YearMonthEventItem {
    data class YearHeader(val year: Int) : YearMonthEventItem
    data class MonthHeader(val month: Int) : YearMonthEventItem
    data class Event(val event: RecordEvent) :YearMonthEventItem
}