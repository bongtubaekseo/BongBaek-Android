package com.bongtu.baekseo.data.datasourceimpl.profile

import com.bongtu.baekseo.data.datasource.profile.ProfileDataSource
import com.bongtu.baekseo.data.dto.profile.ProfileInfoDto
import com.bongtu.baekseo.data.service.profile.ProfileService
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileService: ProfileService,
) : ProfileDataSource {
    override suspend fun getProfileInfo() = profileService.getProfileInfo()

    override suspend fun putProfileInfo(
        request: ProfileInfoDto,
    ) = profileService.putProfileInfo(request)
}
