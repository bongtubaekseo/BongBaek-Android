package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.profile.ProfileInfoDto
import com.bongtu.baekseo.data.model.profile.ProfileInfo

fun ProfileInfoDto.toModel() = ProfileInfo(
    memberName = memberName,
    memberBirthday = memberBirthday,
    memberIncome = memberIncome,
)