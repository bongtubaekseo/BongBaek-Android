package com.bongtu.baekseo.core.common.app

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAppRestarter(
        impl: AppRestarterImpl,
    ): AppRestarter
}
