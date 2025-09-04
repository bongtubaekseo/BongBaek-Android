package com.bongtu.baekseo.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatter {

    private val INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val KOREAN_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    fun formatToKorean(dateString: String): String = try {
        val date = LocalDate.parse(dateString, INPUT_DATE_FORMATTER)
        date.format(KOREAN_DATE_FORMATTER)
    } catch (e: DateTimeParseException) {
        "-"
    }

    fun getYearMonth(dateString: String): Pair<Int, Int> = try {
        val date = LocalDate.parse(dateString, INPUT_DATE_FORMATTER)
        date.year to date.monthValue
    } catch (_: Exception) {
        0 to 0
    }
}