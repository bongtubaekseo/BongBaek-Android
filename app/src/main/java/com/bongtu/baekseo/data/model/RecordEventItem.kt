package com.bongtu.baekseo.data.model

import java.time.LocalDate

data class RecordEventItem(
    val eventId: String,
    val hostName: String,
    val hostNickName: String,
    val category: String,
    val relationship: String,
    val cost: Int,
    val eventDate: LocalDate,
)