package com.bongtu.baekseo.core.common.type

import androidx.annotation.DrawableRes
import com.bongtu.baekseo.R.drawable.ic_checkbox_gray
import com.bongtu.baekseo.R.drawable.ic_checkbox_primary

enum class CheckBoxType(
    @DrawableRes val checkedIcon: Int,
    val size: Int,
) {
    PRIMARY(
        checkedIcon = ic_checkbox_primary,
        size = 24,
    ),
    GRAY(
        checkedIcon = ic_checkbox_gray,
        size = 30,
    ),
}
