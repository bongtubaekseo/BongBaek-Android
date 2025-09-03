package com.bongtu.baekseo.data.datasource.profile

import com.bongtu.baekseo.core.network.model.BaseResponse
import com.bongtu.baekseo.data.dto.profile.ProfileInfoDto

interface ProfileDataSource {
    suspend fun getProfileInfo(): BaseResponse<ProfileInfoDto>

    suspend fun putProfileInfo(
        request: ProfileInfoDto,
    ): BaseResponse<Unit>
}
