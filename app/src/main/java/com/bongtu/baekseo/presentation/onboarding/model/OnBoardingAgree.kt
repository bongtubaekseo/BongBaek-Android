package com.bongtu.baekseo.presentation.onboarding.model

import androidx.annotation.StringRes

data class OnBoardingAgree(
    @StringRes val titleRes: Int,
    val isDescription: Boolean,
    val isArrowVisible: Boolean,
    val url: String? = null,
)