package com.bongtu.baekseo.data.model

import androidx.annotation.StringRes

data class OnBoardingAgree(
    @StringRes val titleRes: Int,
    val isDescription: Boolean,
    val isArrowVisible: Boolean,
    var isChecked: Boolean = false,
)