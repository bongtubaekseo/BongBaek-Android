package com.bongtu.baekseo.data.di.profile

import com.bongtu.baekseo.data.datasource.profile.ProfileDataSource
import com.bongtu.baekseo.data.datasourceimpl.profile.ProfileDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindProfileDataSource(
        dataSourceImpl: ProfileDataSourceImpl,
    ): ProfileDataSource
}
