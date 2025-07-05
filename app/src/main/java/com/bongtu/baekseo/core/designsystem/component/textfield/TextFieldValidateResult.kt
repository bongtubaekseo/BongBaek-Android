package com.bongtu.baekseo.core.designsystem.component.textfield

import androidx.compose.runtime.Immutable

/**
 *  TextField Validate Result
 *  텍스트 필드의 유효성 검사 결과를 나타내는 sealed class
 */
@Immutable
sealed class TextFieldValidateResult {
    abstract val message: String?

    data object Default : TextFieldValidateResult() {
        override val message: String?
            get() = null
    }

    data class Error(
        private val errorMessage: String
    ) : TextFieldValidateResult() {
        override val message: String
            get() = errorMessage
    }
}