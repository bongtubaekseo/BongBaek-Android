package com.bongtu.baekseo.data.repository.map

import com.bongtu.baekseo.data.model.map.Place
import kotlinx.collections.immutable.ImmutableList

interface KakaoMapRepository {
    suspend fun searchPlaces(
        query: String,
        x: Double? = null,
        y: Double? = null,
    ): Result<ImmutableList<Place>>
}
