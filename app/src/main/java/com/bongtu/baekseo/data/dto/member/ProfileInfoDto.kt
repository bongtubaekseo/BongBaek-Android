package com.bongtu.baekseo.data.dto.member

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoDto(
    @SerialName("memberName")
    val memberName: String,
    @SerialName("memberBirthday")
    val memberBirthday: String,
    @SerialName("memberIncome")
    val memberIncome: String,
)