package com.bongtu.baekseo.core.common.type

import androidx.annotation.StringRes
import com.bongtu.baekseo.R.string.date_picker_description_birth
import com.bongtu.baekseo.R.string.date_picker_description_date

enum class DatePickerDialogType(
    @StringRes val descriptionId: Int,
) {
    BIRTH(
        descriptionId = date_picker_description_birth,
    ),
    DATE(
        descriptionId = date_picker_description_date,
    ),
    DATE_PRESENT(
        descriptionId = date_picker_description_date,
    ),
    DATE_FUTURE( // 미래 날짜 타입 추가
        descriptionId = date_picker_description_date,
    ),
}
