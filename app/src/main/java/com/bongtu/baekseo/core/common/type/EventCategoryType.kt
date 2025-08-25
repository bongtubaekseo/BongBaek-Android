package com.bongtu.baekseo.core.common.type

enum class EventCategoryType(
    val label: String,
) {
    ALL(label = "전체"),
    WEDDING(label = EventType.WEDDING.label),
    FUNERAL(label = EventType.FUNERAL.label),
    FIRST_BD(label = EventType.FIRST_BD.label),
    BIRTHDAY(label = EventType.BIRTHDAY.label);
}