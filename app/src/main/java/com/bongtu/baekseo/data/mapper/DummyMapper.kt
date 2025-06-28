package com.bongtu.baekseo.data.mapper

import com.bongtu.baekseo.data.dto.GetUserListResponse
import com.bongtu.baekseo.data.model.DummyUser

fun GetUserListResponse.UserData.toDummyUser() = DummyUser(
    id = this.id ?: 0,
    email = this.email.orEmpty(),
    firstName = this.firstName.orEmpty(),
    lastName = this.lastName.orEmpty(),
    profileImage = this.profileImage.orEmpty(),
)