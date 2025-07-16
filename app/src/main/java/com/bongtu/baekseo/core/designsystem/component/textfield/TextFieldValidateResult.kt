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

    companion object {
        private val SPECIAL_CHAR_REGEX = Regex("[!@#\$%^&*(),.?\":{}|<>]")
        private const val ERROR_LENGTH_MESSAGE = "2자 이상 10자 이내로 입력해주세요"
        private const val ERROR_SPECIAL_CHARACTER_MESSAGE = "특수문자는 사용할 수 없어요"
        private const val ERROR_ZERO_COST_MESSAGE = "1원 이상 적어주세요"

        fun validate(input: String): TextFieldValidateResult {
            return when {
                input.length < 2 || input.length > 10 ->
                    Error(ERROR_LENGTH_MESSAGE)

                SPECIAL_CHAR_REGEX.containsMatchIn(input) ->
                    Error(ERROR_SPECIAL_CHARACTER_MESSAGE)

                else -> Default
            }
        }

        fun validateCost(cost: String): TextFieldValidateResult {
            val number = cost.toIntOrNull() ?: 0

            return when {
                number < 1 -> Error(ERROR_ZERO_COST_MESSAGE)
                else -> Default
            }
        }
    }
}
