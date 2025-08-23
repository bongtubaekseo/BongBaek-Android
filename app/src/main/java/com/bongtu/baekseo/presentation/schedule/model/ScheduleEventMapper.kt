package com.bongtu.baekseo.presentation.schedule.model

import com.bongtu.baekseo.core.util.toFormattedYearWithMonthPair
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun ImmutableList<ScheduleEvent>.toYearMonthEventItemList(): ImmutableList<ScheduleYearMonthEventItem> {
    val result = mutableListOf<ScheduleYearMonthEventItem>()
    var lastYear: Int? = null
    var lastMonth: Int? = null

    this.forEach { event ->
        val (year, month) = event.eventDate.toFormattedYearWithMonthPair()

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

    return result.toImmutableList()
}
