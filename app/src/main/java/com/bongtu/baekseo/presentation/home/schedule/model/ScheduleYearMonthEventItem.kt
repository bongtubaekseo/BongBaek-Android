package com.bongtu.baekseo.presentation.home.schedule.model

import com.bongtu.baekseo.data.model.RecordEvent

sealed interface ScheduleYearMonthEventItem {
    data class YearHeader(val year: Int) : ScheduleYearMonthEventItem
    data class MonthHeader(val month: Int) : ScheduleYearMonthEventItem
    data class Event(val event: ScheduleEvent) : ScheduleYearMonthEventItem
}