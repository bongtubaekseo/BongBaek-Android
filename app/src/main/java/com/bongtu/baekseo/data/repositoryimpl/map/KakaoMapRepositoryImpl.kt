package com.bongtu.baekseo.data.repositoryimpl.map

import com.bongtu.baekseo.data.datasource.map.KakaoMapDataSource
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.data.repository.map.KakaoMapRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import retrofit2.HttpException
import javax.inject.Inject

class KakaoMapRepositoryImpl @Inject constructor(
    private val dataSource: KakaoMapDataSource,
) : KakaoMapRepository {
    override suspend fun searchPlaces(
        query: String,
        x: Double?,
        y: Double?,
    ): Result<ImmutableList<Place>> = runCatching {
        val response = dataSource.searchPlaces(query, x, y)

        if (response.isSuccessful) response.body()?.documents.orEmpty()
        else throw HttpException(response)
    }.mapCatching {
        it.map { it.toModel() }.toImmutableList()
    }.recoverCatching { throwable ->
        emptyList<Place>().toImmutableList()
    }
}
