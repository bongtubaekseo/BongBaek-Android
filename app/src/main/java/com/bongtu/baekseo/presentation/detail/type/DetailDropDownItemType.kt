package com.bongtu.baekseo.presentation.detail.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class DetailDropDownItemType(
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int,
    val trailingType: DetailDropDownTrailingType? = null,
)

@Immutable
sealed interface DetailDropDownTrailingType {
    @Immutable
    data class TrailingText(val text: String) : DetailDropDownTrailingType

    @Immutable
    data class TrailingCost(val text: String) : DetailDropDownTrailingType

    @Immutable
    data class TrailingChip(val text: String) : DetailDropDownTrailingType
}
