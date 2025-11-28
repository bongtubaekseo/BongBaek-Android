package com.bongtu.baekseo.data.di.content

import com.bongtu.baekseo.data.repository.content.ContentsRepository
import com.bongtu.baekseo.data.repositoryimpl.content.ContentsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ContentRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindContentRepository(
        repositoryImpl: ContentsRepositoryImpl,
    ): ContentsRepository
}
