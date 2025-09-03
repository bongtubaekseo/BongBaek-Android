package com.bongtu.baekseo.data.model.profile

import kotlinx.serialization.SerialName

data class ProfileInfo(
    val memberName: String,
    val memberBirthday: String,
    val memberIncome: String,
)