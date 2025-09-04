package com.bongtu.baekseo.data.di.member

import com.bongtu.baekseo.data.repository.member.MemberRepository
import com.bongtu.baekseo.data.repositoryimpl.member.MemberRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MemberRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMemberRepository(
        repositoryImpl: MemberRepositoryImpl,
    ): MemberRepository
}