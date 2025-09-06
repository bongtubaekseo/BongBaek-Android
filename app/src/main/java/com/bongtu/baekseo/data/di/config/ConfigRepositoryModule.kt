package com.bongtu.baekseo.data.di.config

import com.bongtu.baekseo.data.repository.config.ConfigRepository
import com.bongtu.baekseo.data.repositoryimpl.config.ConfigRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConfigRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindConfigRepository(
        repositoryImpl: ConfigRepositoryImpl
    ): ConfigRepository
}
