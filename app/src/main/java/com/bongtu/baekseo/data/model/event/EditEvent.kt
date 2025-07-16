package com.bongtu.baekseo.data.model.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditEvent(
    val eventId: String,
    val hostName: String,
    val hostNickname: String,
    val eventCategory: String,
    val relationship: String,
    val cost: Int,
    val isEventParticipated: Boolean,
    val eventDate: String,
    val note: String,
    val placeId: String,
    val placeName: String,
    val address: String,
    val roadAddress: String,
    val latitude: Double,
    val longitude: Double,
) : Parcelable