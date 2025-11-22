package com.bongtu.baekseo.data.di.content

import com.bongtu.baekseo.data.datasource.content.ContentDataSource
import com.bongtu.baekseo.data.datasourceimpl.content.ContentDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ContentDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindContentDataSource(
        dataSourceImpl: ContentDataSourceImpl,
    ): ContentDataSource
}
