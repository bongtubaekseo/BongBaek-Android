package com.bongtu.baekseo.core.util

import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

object DateValidator {
    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")
    fun validateDate(
        input: String,
        type: DatePickerDialogType,
    ): String {
        val date = runCatching { LocalDate.parse(input, DATE_FORMATTER) }
            .getOrNull() ?: return "유효한 날짜 형식이 아니에요"
        val today = LocalDate.now()

        return when (type) {
            DatePickerDialogType.BIRTH -> {
                if (Period.between(date, today).years < 14) "만 14세 이상부터 사용할 수 있어요"
                else ""
            }

            DatePickerDialogType.DATE_PRESENT -> {
                if (date.isBefore(today)) "앞으로 다가올 일정만 입력할 수 있어요"
                else ""
            }

            DatePickerDialogType.DATE -> ""
        }
    }
}
