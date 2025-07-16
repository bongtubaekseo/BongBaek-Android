package com.bongtu.baekseo.core.common.type

enum class AttendType(
    val label: String,
    val isAttended: Boolean,
) {
    ATTEND(
        label = "참석",
        isAttended = true,
    ),
    ABSENT(
        label = "불참석",
        isAttended = false,
    );
}