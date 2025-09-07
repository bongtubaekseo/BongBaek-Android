package com.bongtu.baekseo.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    private val LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val KOREAN_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    private val NUMERIC_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun formatToKorean(dateString: String): String = try {
        val date = LocalDate.parse(dateString, LOCAL_DATE_FORMATTER)
        date.format(KOREAN_DATE_FORMATTER)
    } catch (_: Exception) {
        "-"
    }

    fun getYearMonth(dateString: String): Pair<Int, Int> = try {
        val date = LocalDate.parse(dateString, LOCAL_DATE_FORMATTER)
        date.year to date.monthValue
    } catch (_: Exception) {
        0 to 0
    }

    fun formatNumericToLocalDate(dateString: String): String = try {
        val date = LocalDate.parse(dateString, NUMERIC_DATE_FORMATTER)
        date.format(LOCAL_DATE_FORMATTER)
    } catch (_: Exception) {
        ""
    }

    fun formatLocalDateToNumeric(dateString: String): String = try {
        val date = LocalDate.parse(dateString, LOCAL_DATE_FORMATTER)
        date.format(NUMERIC_DATE_FORMATTER)
    } catch (_: Exception) {
        ""
    }
}