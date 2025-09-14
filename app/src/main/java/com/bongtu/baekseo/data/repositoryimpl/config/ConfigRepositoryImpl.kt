package com.bongtu.baekseo.data.repositoryimpl.config

import com.bongtu.baekseo.data.repository.config.ConfigRepository
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "ConfigRepositoryImpl"
private const val LATEST_VERSION = "latest_version_android"

class ConfigRepositoryImpl @Inject constructor(

) : ConfigRepository {
    override suspend fun fetchRemoteConfigInfo(): String {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf(LATEST_VERSION to "0.0.0"))

        return try {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getString(LATEST_VERSION)
        } catch (t: Throwable) {
            Timber.tag(TAG).d("Failed to get Latest Version : $t")
            remoteConfig.getString(LATEST_VERSION)
        }
    }
}
