package com.bongtu.baekseo.data.di.event

import com.bongtu.baekseo.data.repository.event.EventRepository
import com.bongtu.baekseo.data.repositoryimpl.event.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEventRepository(
        repositoryImpl: EventRepositoryImpl,
    ): EventRepository
}
