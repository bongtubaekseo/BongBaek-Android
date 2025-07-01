package com.bongtu.baekseo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BongBaekApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        setNightMode()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.Forest.plant(Timber.DebugTree())
    }

    private fun setNightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
