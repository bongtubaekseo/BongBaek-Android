package com.bongtu.baekseo.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toFormattedDateWithDay(): Pair<String, String> {
    val date = this.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val day = this.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, Locale.KOREA)
    return date to day
}

fun LocalDate.toFormattedShortMonth(): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
    return this.format(formatter)
}

fun LocalDate.toFormattedYearWithMonth(): Pair<Int, Int> {
    val date = this.format(DateTimeFormatter.ofPattern("yyyy-MM"))
    val (year, month) = date.split("-")
    return year.toInt() to month.toInt()
}