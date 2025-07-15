package com.bongtu.baekseo.core.util

/**
 * 앱 전역적으로 사용되는 Validator입니다.
 * 이름 및 닉네임에는 validateName을, 금액 필드에는 validateCost를 사용하면 됩니다.
 */
object TextFieldValidator {
    private val specialCharRegex = Regex("[!@#$%^&*(),.?\":{}|<>]")

    fun validateName(name: String) = when {
        specialCharRegex.containsMatchIn(name) -> "특수문자는 사용할 수 없어요."
        name.length < 2 || name.length > 10 -> "2자 이상 10자 이내로 입력해주세요."
        else -> null
    }

    fun validateCost(cost: String) = when {
        cost.toInt() < 1 ->"1원 이상 적어주세요"
        else -> null
    }
}
