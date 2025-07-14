package com.bongtu.baekseo.data.datasource.map

import com.bongtu.baekseo.data.dto.map.KakaoMapResponse
import retrofit2.Response

interface KakaoMapDataSource {
    suspend fun searchPlaces(
        query: String,
        x: Double?,
        y: Double?,
    ): Response<KakaoMapResponse>
}
