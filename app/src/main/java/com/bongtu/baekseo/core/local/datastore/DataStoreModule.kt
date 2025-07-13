package com.bongtu.baekseo.core.local.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val TOKEN_PREFERENCE_NAME = "token_preference"
    private const val USER_NAME_PREFERENCE_NAME = "user_name_preference"

    private val Context.provideDataStore by preferencesDataStore(TOKEN_PREFERENCE_NAME)
    private val Context.provideUsernameDataStore by preferencesDataStore(USER_NAME_PREFERENCE_NAME)

    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context,
    ) = TokenDataStore(context.provideDataStore)

    @Provides
    @Singleton
    fun provideUsernameDataStore(
        @ApplicationContext context: Context,
    ) = UsernameDataStore(context.provideUsernameDataStore)
}
