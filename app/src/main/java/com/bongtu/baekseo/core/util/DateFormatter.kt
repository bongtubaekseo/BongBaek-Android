package com.bongtu.baekseo.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatter {

    private val INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val OUTPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

    /**
     * "yyyy-MM-dd" 형식의 날짜 문자열을 "yyyy년 MM월 dd일" 형식으로 변환합니다.
     * @param dateString 변환할 날짜 문자열
     * @return 변환된 문자열, 형식 변환에 실패하면 원본 문자열을 반환합니다.
     */
    fun formatDate(dateString: String?): String {
        if (dateString.isNullOrBlank()) {
            return "-"
        }

        return try {
            val date = LocalDate.parse(dateString, INPUT_DATE_FORMATTER)
            date.format(OUTPUT_DATE_FORMATTER)
        } catch (e: DateTimeParseException) {
            "-"
        }
    }
}