package com.bongtu.baekseo.data.repository.profile

import com.bongtu.baekseo.data.model.profile.ProfileInfo

interface ProfileRepository {
    suspend fun getProfileInfo(): Result<ProfileInfo>

    suspend fun putProfileInfo(
        profileInfo: ProfileInfo,
    ): Result<Unit>
}