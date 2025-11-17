package com.bongtu.baekseo.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpRequest (
    @SerialName("oauthId")
    val oauthId: String,
    @SerialName("oauthProvider")
    val oauthProvider: String,
    @SerialName("memberName")
    val memberName: String,
    @SerialName("memberBirthday")
    val memberBirthday: String,
    @SerialName("memberIncome")
    val memberIncome: String,
)