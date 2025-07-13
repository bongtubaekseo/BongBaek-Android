package com.bongtu.baekseo.data.di.map

import com.bongtu.baekseo.core.network.qualifier.Kakao
import com.bongtu.baekseo.data.service.map.KakaoMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapServiceModule {
    @Provides
    @Singleton
    fun provideKakaoMapService(@Kakao retrofit: Retrofit): KakaoMapService = retrofit.create()
}
