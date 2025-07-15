package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpRequest (
    @SerialName("kakaoId")
    val kakaoId: Long,
    @SerialName("appleId")
    val appleId: Long? = null,
    @SerialName("memberName")
    val memberName: String,
    @SerialName("memberBirthday")
    val memberBirthday: String,
    @SerialName("memberIncome")
    val memberIncome: String,
)