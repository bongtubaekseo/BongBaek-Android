package com.bongtu.baekseo.domain.usecase.config

import com.bongtu.baekseo.core.local.datastore.ConfigDataStore
import com.bongtu.baekseo.data.repository.config.ConfigRepository
import net.swiftzer.semver.SemVer.Companion.parse
import javax.inject.Inject

class CheckAppVersionUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
    private val configDataStore: ConfigDataStore,
) {
    suspend operator fun invoke(
        appVersion: String,
    ) = runCatching {
        val remoteConfigVersion = configRepository.fetchRemoteConfigInfo()
        val latestVersion = parse(remoteConfigVersion)
        val currentVersion = parse(appVersion)

        val updateFlag = when {
            currentVersion.major < latestVersion.major -> true
            currentVersion.major == latestVersion.major && currentVersion.minor < latestVersion.minor -> true
            else -> false
        }

        configDataStore.setUpdateFlag(updateFlag)
    }.onFailure {
        configDataStore.setUpdateFlag(false)
    }
}
