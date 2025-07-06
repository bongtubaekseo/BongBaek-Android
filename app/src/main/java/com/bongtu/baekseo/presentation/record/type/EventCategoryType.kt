package com.bongtu.baekseo.presentation.record.type

import com.bongtu.baekseo.core.common.type.EventType

enum class EventCategoryType(
    val label: String,
) {
    ALL(label = "전체"),
    WEDDING(label = EventType.WEDDING.label),
    FUNERAL(label = EventType.FUNERAL.label),
    FIRST_BD(label = EventType.FIRST_BD.label),
    BIRTHDAY(label = EventType.ANNIVERSARY.label); // TODO: 생일 변경 예정
}