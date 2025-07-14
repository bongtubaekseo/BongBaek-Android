package com.bongtu.baekseo.data.service.map

import com.bongtu.baekseo.data.dto.map.KakaoMapResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoMapService {
    @GET("v2/local/search/keyword.json")
    suspend fun searchPlaces(
        @Query("query") query: String,
        @Query("x") x: Double? = null,
        @Query("y") y: Double? = null,
        @Query("radius") radius: Int = 5000,
    ): Response<KakaoMapResponse>
}
