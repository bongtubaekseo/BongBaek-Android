package com.bongtu.baekseo.data.di.member

import com.bongtu.baekseo.data.service.member.MemberService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MemberServiceModule {
    @Provides
    @Singleton
    fun provideMemberService(retrofit: Retrofit): MemberService = retrofit.create()
}