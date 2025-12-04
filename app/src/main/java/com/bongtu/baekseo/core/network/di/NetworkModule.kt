package com.bongtu.baekseo.core.network.di

import com.bongtu.baekseo.BuildConfig
import com.bongtu.baekseo.BuildConfig.BASE_URL
import com.bongtu.baekseo.BuildConfig.KAKAO_BASE_URL
import com.bongtu.baekseo.core.local.datastore.ApiKeyDataStore
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.network.HeaderInterceptor
import com.bongtu.baekseo.core.network.KakaoHeaderInterceptor
import com.bongtu.baekseo.core.network.TokenAuthenticator
import com.bongtu.baekseo.core.network.isJsonArray
import com.bongtu.baekseo.core.network.isJsonObject
import com.bongtu.baekseo.core.network.qualifier.JWT
import com.bongtu.baekseo.core.network.qualifier.Kakao
import com.bongtu.baekseo.core.network.qualifier.NoAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val APPLICATION_JSON = "application/json"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { message ->
        when {
            message.isJsonObject() -> Timber.Forest.tag("okhttp").d(JSONObject(message).toString(4))

            message.isJsonArray() -> Timber.Forest.tag("okhttp").d(JSONArray(message).toString(4))

            else -> {
                Timber.Forest.tag("okhttp").d("CONNECTION INFO -> $message")
            }
        }
    }.apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @JWT
    fun provideHeaderInterceptor(
        tokenDataStore: TokenDataStore,
    ): Interceptor = HeaderInterceptor(tokenDataStore)

    @Provides
    @Singleton
    @Kakao
    fun provideKakaoHeaderInterceptor(
        apiKeyDataStore: ApiKeyDataStore,
    ): Interceptor = KakaoHeaderInterceptor(apiKeyDataStore)

    @Provides
    @Singleton
    @JWT
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
        @JWT headerInterceptor: Interceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @Provides
    @Singleton
    @NoAuth
    fun provideNoTokenOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @Kakao
    fun provideKakaoOkHttpClient(
        loggingInterceptor: Interceptor,
        @Kakao kakaoHeaderInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(kakaoHeaderInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @JWT client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @NoAuth
    fun provideNoAuthRetrofit(
        @NoAuth client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    @Kakao
    fun provideKakaoMapRetrofit(
        @Kakao client: OkHttpClient,
        factory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(KAKAO_BASE_URL)
        .client(client)
        .addConverterFactory(factory)
        .build()
}
