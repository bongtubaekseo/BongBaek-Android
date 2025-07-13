package com.bongtu.baekseo.core.common.type

enum class IncomeType(
    val label: String,
) {
    UNDER_200(label = "200만원 미만"),
    OVER_200(label = "200만원 이상"),
    NONE(label = "없음");
}