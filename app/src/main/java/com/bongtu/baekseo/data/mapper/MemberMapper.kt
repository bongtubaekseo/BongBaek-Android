package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.member.ProfileInfoDto
import com.bongtu.baekseo.data.model.profile.ProfileInfo

fun ProfileInfoDto.toModel() = ProfileInfo(
    memberName = memberName,
    memberBirthday = memberBirthday,
    memberIncome = memberIncome,
)