package com.bongtu.baekseo.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/**
 * mmddyyyy -> yyyy-mm-dd
 */
fun String.toFormattedDate(): String {
    if (this.length != 8) return ""
    val month = this.substring(0, 2)
    val day = this.substring(2, 4)
    val year = this.substring(4, 8)
    return "$year-$month-$day"
}

/**
 * yyyy-mm-dd -> yyyy. mm. dd (day)
 */
fun String.toFormattedDateWithDay(): String {
    val parsedDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    val date = parsedDate.format(DateTimeFormatter.ofPattern("yyyy. MM. dd"))
    val day = parsedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    return "$date ($day)"
}

/**
 * yyyy-mm-dd -> yyyy.mm.dd, (day)
 */
fun String.toFormattedDateAndDay(): Pair<String, String> = runCatching {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    val date = localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val day = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    date to day
}.getOrElse { "" to "" }

/**
 * yyyy-mm-dd -> "MMM dd, yyyy"
 */
fun String.toFormattedShortEnglishDate(): String = runCatching {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    val date = localDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH))
    date
}.getOrElse { this }

/**
 * yyyy-mm-dd -> Pair(year, month)
 */
fun String.toFormattedYearWithMonthPair(): Pair<Int, Int> {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    return localDate.year to localDate.monthValue
}

/**
 * yyyy-mm-dd -> MMddyyyy
 */
fun String.toFormattedMonthDayYear(): String = runCatching {
    val parsedDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    parsedDate.format(DateTimeFormatter.ofPattern("MMddyyyy"))
}.getOrElse { this }