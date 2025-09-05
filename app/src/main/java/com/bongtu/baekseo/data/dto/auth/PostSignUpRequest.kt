package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpRequest (
    @SerialName("kakaoId")
    val kakaoId: String,
    @SerialName("appleId")
    val appleId: String? = null,
    @SerialName("memberName")
    val memberName: String,
    @SerialName("memberBirthday")
    val memberBirthday: String,
    @SerialName("memberIncome")
    val memberIncome: String,
)