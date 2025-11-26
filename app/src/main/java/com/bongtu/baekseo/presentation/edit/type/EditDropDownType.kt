package com.bongtu.baekseo.presentation.edit.type

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Immutable
data class EditDropdownColorState(
    val borderColor: Color,
    val textColor: Color,
    val iconColor: Color,
)

enum class EditDropDownType(
) {
    COLLAPSED_EMPTY,
    EXPANDED_EMPTY,
    EXPANDED_FILLED,
    COLLAPSED_FILLED;

    companion object {
        fun from(
            hasValue: Boolean,
            expanded: Boolean,
        ): EditDropDownType =
            when {
                !hasValue && !expanded -> COLLAPSED_EMPTY
                !hasValue && expanded -> EXPANDED_EMPTY
                hasValue && expanded -> EXPANDED_FILLED
                else -> COLLAPSED_FILLED
            }
    }

    @Composable
    fun getColorState(
        isEditable: Boolean,
    ): EditDropdownColorState {
        val colors = BongBaekTheme.colors

        val colorState = when (this) {
            COLLAPSED_EMPTY -> EditDropdownColorState(
                borderColor = colors.transparent,
                textColor = colors.txtFieldPlaceholder,
                iconColor = colors.iconDisabledSecondary,
            )

            EXPANDED_EMPTY -> EditDropdownColorState(
                borderColor = colors.statusFocused,
                textColor = colors.txtFieldValue,
                iconColor = colors.iconInteractiveDefault,
            )

            EXPANDED_FILLED -> EditDropdownColorState(
                borderColor = colors.statusFocused,
                textColor = colors.statusFocused,
                iconColor = colors.statusFocused,
            )

            COLLAPSED_FILLED -> EditDropdownColorState(
                borderColor = colors.transparent,
                textColor = colors.txtFieldValue,
                iconColor = colors.iconInteractiveDefault,
            )
        }

        if (!isEditable) {
            return colorState.copy(
                borderColor = colors.transparent,
                textColor = colors.txtStatusDisabled,
                iconColor = colors.iconDisabledSecondary,
            )
        }

        return colorState
    }
}
