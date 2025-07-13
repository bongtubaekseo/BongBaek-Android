package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.map.KakaoMapResponse
import com.bongtu.baekseo.data.model.map.Place

fun KakaoMapResponse.PlaceDocument.toModel(): Place {
    return Place(
        id = id,
        name = placeName,
        address = addressName,
        roadAddress = roadAddress,
        latitude = y.toDoubleOrNull() ?: 0.0,
        longitude = x.toDoubleOrNull() ?: 0.0,
    )
}
