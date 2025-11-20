package com.bongtu.baekseo.data.di.auth

import com.bongtu.baekseo.core.network.qualifier.NoAuth
import com.bongtu.baekseo.data.service.auth.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(@NoAuth retrofit: Retrofit): AuthService = retrofit.create()
}
