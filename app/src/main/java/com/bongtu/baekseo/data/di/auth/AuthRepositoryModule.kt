package com.bongtu.baekseo.data.di.auth

import com.bongtu.baekseo.data.repository.auth.AuthRepository
import com.bongtu.baekseo.data.repositoryimpl.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        repositoryImpl: AuthRepositoryImpl,
    ): AuthRepository
}