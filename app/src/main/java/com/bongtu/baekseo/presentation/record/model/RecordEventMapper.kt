//package com.bongtu.baekseo.presentation.record.model
//
//import com.bongtu.baekseo.data.model.RecordEvent
//import com.bongtu.baekseo.data.model.event.ScheduleEvent
//import kotlinx.collections.immutable.ImmutableList
//
//fun ImmutableList<ScheduleEvent>.toYearMonthEventItemList(): List<YearMonthEventItem> {
//    val result = mutableListOf<YearMonthEventItem>()
//    var lastYear: Int? = null
//    var lastMonth: Int? = null
//
//    this.forEach { event ->
//        val year = event.eventDate.year
//        val month = event.eventDate.monthValue
//
//        if (year != lastYear) {
//            result.add(YearMonthEventItem.YearHeader(year))
//            lastYear = year
//            lastMonth = null
//        }
//
//        if (month != lastMonth) {
//            result.add(YearMonthEventItem.MonthHeader(month))
//            lastMonth = month
//        }
//
//        result.add(YearMonthEventItem.Event(event))
//    }
//
//    return result
//}
