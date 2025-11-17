package com.bongtu.baekseo.presentation.login.model

import androidx.annotation.StringRes

data class LoginAgree(
    @StringRes val titleRes: Int,
    val isDescription: Boolean,
    val isArrowVisible: Boolean,
    val url: String? = null,
)