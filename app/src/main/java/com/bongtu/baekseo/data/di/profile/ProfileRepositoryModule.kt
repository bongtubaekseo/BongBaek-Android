package com.bongtu.baekseo.data.di.profile

import com.bongtu.baekseo.data.repository.profile.ProfileRepository
import com.bongtu.baekseo.data.repositoryimpl.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProfileRepository(
        repositoryImpl: ProfileRepositoryImpl,
    ): ProfileRepository
}
