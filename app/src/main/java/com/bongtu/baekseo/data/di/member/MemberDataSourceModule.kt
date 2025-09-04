package com.bongtu.baekseo.data.di.member

import com.bongtu.baekseo.data.datasource.member.MemberDataSource
import com.bongtu.baekseo.data.datasourceimpl.member.MemberDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MemberDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindMemberDataSource(
        dataSourceImpl: MemberDataSourceImpl,
    ): MemberDataSource
}