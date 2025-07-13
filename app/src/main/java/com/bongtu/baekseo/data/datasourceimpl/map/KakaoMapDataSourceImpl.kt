package com.bongtu.baekseo.data.datasourceimpl.map

import com.bongtu.baekseo.data.datasource.map.KakaoMapDataSource
import com.bongtu.baekseo.data.service.map.KakaoMapService
import javax.inject.Inject

class KakaoMapDataSourceImpl @Inject constructor(
    private val kakaoMapService: KakaoMapService,
) : KakaoMapDataSource {
    override suspend fun searchPlaces(
        query: String,
        x: Double?,
        y: Double?,
    ) = kakaoMapService.searchPlaces(query, x, y)
}
