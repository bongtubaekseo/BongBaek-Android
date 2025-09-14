package com.bongtu.baekseo.core.network

import android.os.Build.MANUFACTURER
import android.os.Build.MODEL
import android.os.Build.VERSION.SDK_INT
import com.bongtu.baekseo.BuildConfig.APPLICATION_ID
import com.bongtu.baekseo.core.local.datastore.ApiKeyDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject

class KakaoHeaderInterceptor @Inject constructor(
    private val apiKeyDataStore: ApiKeyDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = runBlocking { apiKeyDataStore.getApiKey() }

        val kaHeaderValue = buildString {
            append("sdk/1.0.0 ")
            append("os/android-$SDK_INT ")
            append("lang/${Locale.getDefault().toLanguageTag()} ")
            append("device/${MANUFACTURER}-${MODEL} ")
            append("origin/${APPLICATION_ID}")
        }

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK $apiKey")
            .addHeader("KA", kaHeaderValue)
            .build()

        return chain.proceed(newRequest)
    }
}
