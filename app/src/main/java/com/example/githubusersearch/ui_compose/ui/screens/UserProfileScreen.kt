package com.example.githubusersearch.ui_compose.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.ui_compose.testUserDomainModel
import com.example.githubusersearch.ui_compose.MEDIUM_PADDING
import com.example.githubusersearch.ui_compose.ui.components.ProfileHeader
import com.example.githubusersearch.ui_compose.ui.components.ProfileSummary
import com.example.githubusersearch.ui_compose.ui.components.UsersList
import com.example.githubusersearch.ui_compose.ui.state.UserProfileState
import com.example.githubusersearch.ui_compose.ui.theme.GithubUserSearchTheme
import com.example.githubusersearch.ui_compose.viewmodel.ListType
import com.example.githubusersearch.ui_compose.viewmodel.UserListViewModel
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen(
    userProfileState: UserProfileState,
    navigateToUser: (username: String) -> Unit
) {
    when (userProfileState) {
        is UserProfileState.Loading -> {
            // TODO: Show loading
        }

        is UserProfileState.Error -> {

            // TODO:  Show error
        }

        is UserProfileState.Success -> {
            UserProfile(userDomainModel = userProfileState.userDomainModel, navigateToUser)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun UserProfile(
    userDomainModel: UserDomainModel,
    navigateToUser: (username: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ProfileHeader(userDomainModel = userDomainModel)
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        val tabs = listOf(
            "Description",
            " ${userDomainModel.followers} Followers",
            "${userDomainModel.following} Following"
        )
        var selectedTabIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        val pageState = rememberPagerState {
            tabs.size
        }
        val coroutineScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = index == selectedTabIndex, onClick = {
                    selectedTabIndex = index
                    coroutineScope.launch {
                        pageState.scrollToPage(index)
                    }
                }) {
                    Text(
                        modifier = Modifier.padding(MEDIUM_PADDING),
                        text = title,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        HorizontalPager(state = pageState) {
            when (it) {
                0 -> ProfileSummary(summary = userDomainModel.description)

                1 -> SetUsersList(userDomainModel.username, ListType.FOLLOWERS, navigateToUser)

                2 -> SetUsersList(userDomainModel.username, ListType.FOLLOWING, navigateToUser)

            }
        }

    }
}

@Composable
fun SetUsersList(username: String, listType: ListType, navigateToUser: (username: String) -> Unit) {
    val viewModel: UserListViewModel = hiltViewModel(key = "$username+$listType")

    LaunchedEffect(key1 = username) {
        viewModel.getList(listType, username)
    }

    val userList = viewModel.userList.value
    val users = userList.collectAsLazyPagingItems()

    UsersList(users = users) {
        navigateToUser(it)
    }

}

@Preview(showBackground = true)
@Composable
private fun UserProfileScreenPreview() {
    GithubUserSearchTheme {
        UserProfile(userDomainModel = testUserDomainModel, navigateToUser = {})
    }
}