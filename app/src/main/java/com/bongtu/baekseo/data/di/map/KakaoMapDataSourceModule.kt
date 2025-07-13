package com.bongtu.baekseo.data.di.map

import com.bongtu.baekseo.data.datasource.map.KakaoMapDataSource
import com.bongtu.baekseo.data.datasourceimpl.map.KakaoMapDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoMapDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindKakaoMapDataSource(
        dataSourceImpl: KakaoMapDataSourceImpl,
    ): KakaoMapDataSource
}
