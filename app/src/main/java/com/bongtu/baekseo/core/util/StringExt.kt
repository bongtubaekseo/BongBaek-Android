package com.bongtu.baekseo.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/**
 * yyyy-mm-dd -> yyyy. mm. dd (day)
 */
fun String.toFormattedDateWithDay(): String = try {
    val parsedDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    val date = parsedDate.format(DateTimeFormatter.ofPattern("yyyy. MM. dd"))
    val day = parsedDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    "$date ($day)"
} catch (_: Exception) { this }

/**
 * yyyy-mm-dd -> yyyy.mm.dd, (day)
 */
fun String.toFormattedDateAndDay(): Pair<String, String> = try {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    val date = localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    val day = localDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    date to day
} catch (_: Exception) { "" to "" }

/**
 * yyyy-mm-dd -> "MMM dd, yyyy"
 */
fun String.toFormattedShortEnglishDate(): String = try {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    val date = localDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH))
    date
} catch (_: Exception) { this }

/**
 * yyyy-mm-dd -> Pair(year, month)
 */
fun String.toFormattedYearWithMonthPair(): Pair<Int, Int> = try {
    val localDate = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
    localDate.year to localDate.monthValue
} catch (_: Exception) { 0 to 0 }

/**
 * yyyyMMdd -> yyyy-MM-dd
 */
fun String.toLocalDateFormat(): String = try {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val date = LocalDate.parse(this, formatter)
    date.toString()
} catch (_: Exception) { this }