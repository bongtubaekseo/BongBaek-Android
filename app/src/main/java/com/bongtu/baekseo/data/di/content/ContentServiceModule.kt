package com.bongtu.baekseo.data.di.content

import com.bongtu.baekseo.data.service.content.ContentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentServiceModule {
    @Provides
    @Singleton
    fun provideContentService(retrofit: Retrofit): ContentService = retrofit.create()
}
