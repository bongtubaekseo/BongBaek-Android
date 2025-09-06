package com.bongtu.baekseo.domain.usecase.config

import com.bongtu.baekseo.data.repository.config.ConfigRepository
import jakarta.inject.Inject
import net.swiftzer.semver.SemVer.Companion.parse

class CheckAppVersionUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
) {
    suspend operator fun invoke(
        appVersion: String,
    ): Boolean {
        val remoteConfigVersion = configRepository.fetchRemoteConfigInfo()
        val latestVersion = parse(remoteConfigVersion)
        val currentVersion = parse(appVersion)

        return when {
            currentVersion.major < latestVersion.major -> true
            currentVersion.major == latestVersion.major && currentVersion.minor < latestVersion.minor -> true
            else -> false
        }
    }
}
