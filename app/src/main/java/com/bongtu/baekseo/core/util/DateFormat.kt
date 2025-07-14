package com.bongtu.baekseo.core.util

fun String.toFormattedDate(): String {
    if (this.length != 8) return ""
    val month = this.substring(0, 2)
    val day = this.substring(2, 4)
    val year = this.substring(4, 8)
    return "$year-$month-$day"
}