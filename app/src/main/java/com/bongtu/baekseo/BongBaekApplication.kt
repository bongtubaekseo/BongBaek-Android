package com.bongtu.baekseo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BongBaekApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKakaoSkd()
        setNightMode()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.Forest.plant(Timber.DebugTree())
    }

    private fun setNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun initKakaoSkd() {
        KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
    }
}
