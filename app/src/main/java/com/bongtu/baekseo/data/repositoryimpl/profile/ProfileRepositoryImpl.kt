package com.bongtu.baekseo.data.repositoryimpl.profile

import com.bongtu.baekseo.data.datasource.profile.ProfileDataSource
import com.bongtu.baekseo.data.dto.member.ProfileInfoDto
import com.bongtu.baekseo.data.mapper.toModel
import com.bongtu.baekseo.data.model.profile.ProfileInfo
import com.bongtu.baekseo.data.repository.profile.ProfileRepository
import jakarta.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource,
) : ProfileRepository {
    override suspend fun getProfileInfo(): Result<ProfileInfo> = runCatching {
        profileDataSource.getProfileInfo()
    }.mapCatching { response ->
        response.data.toModel()
    }

    override suspend fun putProfileInfo(profileInfo: ProfileInfo): Result<Unit> = runCatching {
        profileDataSource.putProfileInfo(
            request = ProfileInfoDto(
                memberName = profileInfo.memberName,
                memberBirthday = profileInfo.memberBirthday,
                memberIncome = profileInfo.memberIncome,
            ),
        )
    }
}