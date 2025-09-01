package com.bongtu.baekseo.core.util

import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import java.time.LocalDate
import java.time.Period

object DateValidator {
    fun validateDate(
        input: String,
        type: DatePickerDialogType,
    ): String {
        if (input.length != 8) return "유효한 날짜 형식이 아닙니다"

        val year = input.substring(0, 4).toIntOrNull()
        val month = input.substring(4, 6).toIntOrNull()
        val day = input.substring(6, 8).toIntOrNull()

        if (month == null || day == null || year == null) return "유효한 날짜 형식이 아닙니다"
        if (month !in 1..12 || day !in 1..31) return "유효한 날짜 형식이 아닙니다"

        val date = LocalDate.of(year, month, day)
        val today = LocalDate.now()

        return when (type) {
            DatePickerDialogType.BIRTH -> {
                if (Period.between(date, today).years < 14) "만 14세 이상만 가입이 가능합니다"
                else ""
            }

            DatePickerDialogType.DATE_PRESENT -> {
                if (date.isBefore(today)) "과거 날짜는 입력할 수 없습니다"
                else ""
            }

            DatePickerDialogType.DATE_FUTURE -> {
                if (date < today) "미래 날짜만 입력 가능합니다"
                else ""
            }

            DatePickerDialogType.DATE -> ""
        }
    }
}
