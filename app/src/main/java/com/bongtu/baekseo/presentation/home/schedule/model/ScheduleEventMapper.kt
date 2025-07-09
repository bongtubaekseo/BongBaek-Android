package com.bongtu.baekseo.presentation.home.schedule.model

import com.bongtu.baekseo.core.util.toFormattedYearWithMonth

fun List<ScheduleEvent>.toYearMonthEventItemList(): List<ScheduleYearMonthEventItem> {
    val result = mutableListOf<ScheduleYearMonthEventItem>()
    var lastYear: Int? = null
    var lastMonth: Int? = null

    this.forEach { event ->
        val (year, month) = event.eventInfo.eventDate.toFormattedYearWithMonth()

        if (year != lastYear) {
            result.add(ScheduleYearMonthEventItem.YearHeader(year))
            lastYear = year
            lastMonth = null
        }

        if (month != lastMonth) {
            result.add(ScheduleYearMonthEventItem.MonthHeader(month))
            lastMonth = month
        }

        result.add(ScheduleYearMonthEventItem.Event(event))
    }

    return result
}
