package com.bongtu.baekseo.data.di.event

import com.bongtu.baekseo.data.datasource.event.EventDataSource
import com.bongtu.baekseo.data.datasourceimpl.event.EventDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class EventDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindEventDataSource(
        dataSourceImpl: EventDataSourceImpl,
    ): EventDataSource
}
