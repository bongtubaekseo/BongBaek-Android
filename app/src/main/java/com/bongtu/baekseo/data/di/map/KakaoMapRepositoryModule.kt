package com.bongtu.baekseo.data.di.map

import com.bongtu.baekseo.data.repository.map.KakaoMapRepository
import com.bongtu.baekseo.data.repositoryimpl.map.KakaoMapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoMapRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindKakaoRepository(
        repositoryImpl: KakaoMapRepositoryImpl,
    ): KakaoMapRepository
}
