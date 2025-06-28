package com.bongtu.baekseo.presentation.dummy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.data.model.DummyUser
import com.bongtu.baekseo.presentation.dummy.DummyContract.DummyState
import com.bongtu.baekseo.presentation.dummy.component.DummyUserItem

@Composable
fun DummyRoute(
    modifier: Modifier = Modifier,
    viewModel: DummyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchDummyUsers()
    }

    DummyScreen(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun DummyScreen(
    uiState: DummyState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState.dummyUsersLoadState) {
            is UiState.Empty -> {
                // 빈 상태 화면
            }

            is UiState.Failure -> {
                // 에러 상태 화면
            }

            is UiState.Loading -> {
                //로딩 상태 화면
            }

            is UiState.Success -> {
                DummyUserListColumn(
                    dummyUsers = uiState.dummyUsersLoadState.data,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
private fun DummyUserListColumn(
    dummyUsers: List<DummyUser>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            dummyUsers, key = { it.id },
        ) { user ->
            DummyUserItem(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                profileImage = user.profileImage,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DummyScreenPreview() {
    DummyUserListColumn(
        dummyUsers = listOf(
            DummyUser(
                id = 1,
                firstName = "Seokjun",
                lastName = "Lee",
                email = "seokjunlee@napjak.com",
                profileImage = "https://avatars.githubusercontent.com/u/101652649?v=4",
            ),
            DummyUser(
                id = 2,
                firstName = "Jongmyung",
                lastName = "Kim",
                email = "jongmyungkim@napjak.com",
                profileImage = "https://avatars.githubusercontent.com/u/52846796?v=4",
            ),
            DummyUser(
                id = 3,
                firstName = "Jaewon",
                lastName = "Jang",
                email = "jaewonjang@napjak.com",
                profileImage = "https://avatars.githubusercontent.com/u/182846193?v=4",
            ),
            DummyUser(
                id = 4,
                firstName = "Chaerin",
                lastName = "Kim",
                email = "chaerinkim@napjak.com",
                profileImage = "https://avatars.githubusercontent.com/u/89915076?v=4",
            ),
            DummyUser(
                id = 5,
                firstName = "Yeonjeen",
                lastName = "Lee",
                email = "yeonjeelee@napjak.com",
                profileImage = "https://avatars.githubusercontent.com/u/144861180?v=4",
            ),
        )
    )
}
