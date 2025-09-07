package com.bongtu.baekseo.core.common.type

enum class IncomeType(
    val label: String,
) {
    UNDER_200(label = "200만원 미만"),
    OVER_200(label = "200만원 이상"),
    NONE(label = "없음");

    companion object {
        fun getIncomeType(label: String): IncomeType {
            return when (label) {
                "UNDER200" -> UNDER_200
                "OVER200" -> OVER_200
                else -> NONE
            }
        }
    }
}