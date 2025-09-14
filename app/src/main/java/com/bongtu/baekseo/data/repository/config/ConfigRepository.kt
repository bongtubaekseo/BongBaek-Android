package com.bongtu.baekseo.data.repository.config

interface ConfigRepository {
    suspend fun fetchRemoteConfigInfo(): String
}
