package com.bongtu.baekseo.data.di.event

import com.bongtu.baekseo.data.service.event.EventService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventServiceModule {
    @Provides
    @Singleton
    fun provideEventService(retrofit: Retrofit): EventService = retrofit.create()
}
