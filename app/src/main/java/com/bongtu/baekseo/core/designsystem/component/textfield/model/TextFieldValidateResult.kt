package com.bongtu.baekseo.core.designsystem.component.textfield.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class TextFieldValidateResult {
    abstract val message: String?

    data object Default : TextFieldValidateResult() {
        override val message: String?
            get() = null
    }

    data class Error(private val errorMessage: String
    ) : TextFieldValidateResult() {
        override val message: String
            get() = errorMessage
    }
}