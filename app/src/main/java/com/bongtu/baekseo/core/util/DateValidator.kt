package com.bongtu.baekseo.core.util

fun isValidDate(input: String): Boolean {
    if (input.length != 8) return false

    val month = input.substring(0, 2).toIntOrNull() ?: return false
    val day = input.substring(2, 4).toIntOrNull() ?: return false
    val year = input.substring(4, 8).toIntOrNull() ?: return false

    // 월: 1~12, 일: 1~31
    if (month !in 1..12 || day !in 1..31) return false

    // 만 14세 이상 (2025년 기준 2011년까지)
    if (year > 2011) return false

    // 실제 날짜 유효성 확인
    return try {
        java.time.LocalDate.of(year, month, day)
        true
    } catch (e: Exception) {
        false
    }
}