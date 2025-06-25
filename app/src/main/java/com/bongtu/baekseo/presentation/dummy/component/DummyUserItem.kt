package com.bongtu.baekseo.presentation.dummy.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

/**
 * DummyUserItem
 */
@Composable
fun DummyUserItem(
    firstName: String,
    lastName: String,
    email: String,
    profileImage: String,
    modifier: Modifier = Modifier,
) {
    val context by rememberUpdatedState(LocalContext.current)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(profileImage).crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
        )

        Column(
            modifier = Modifier
                .weight(4f)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = "$firstName $lastName",
                fontSize = 16.sp,
            )

            Text(
                text = email,
                fontSize = 10.sp,
                color = Color.Gray,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DummyUserItemPreview() {
    DummyUserItem(
        firstName = "Seokjun",
        lastName = "Lee",
        email = "seokjunlee@napjak.com",
        profileImage = "https://reqres.in/img/faces/7-image.jpg",
        modifier = Modifier.fillMaxWidth(),
    )
}
