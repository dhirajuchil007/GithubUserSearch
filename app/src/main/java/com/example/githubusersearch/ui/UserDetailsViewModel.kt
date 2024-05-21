package com.example.githubusersearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.githubusersearch.domain.GetUserUseCase
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.ui.paging.UserPager
import com.example.githubusersearch.ui.states.UserDetailsState
import com.example.githubusersearch.ui.states.UserFollowState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    val getUserUseCase: GetUserUseCase,
    val userPager: UserPager
) : ViewModel() {

    val state = MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)

    val followersState = MutableStateFlow<UserFollowState>(UserFollowState.Loading)
    val followingState = MutableStateFlow<UserFollowState>(UserFollowState.Loading)


    fun getUser(userName: String?) {
        if (userName.isNullOrEmpty()) {
            state.value = UserDetailsState.Error("Invalid username $userName")
            return
        }

        state.value = UserDetailsState.Loading
        viewModelScope.launch {
            when (val result = getUserUseCase(userName)) {
                is Result.Success -> {
                    state.value = UserDetailsState.ShowUser(result.data)
                }

                is Result.Error -> {
                    state.value = UserDetailsState.Error(result.error.name)
                }
            }
        }
    }

    fun getUserFollowers(userName: String?) {
        if (userName.isNullOrEmpty()) {
            followersState.value = UserFollowState.Error("Invalid username $userName")
            return
        }

        followersState.value = UserFollowState.Loading
        viewModelScope.launch {
            userPager.getFollowersPage(userName).cachedIn(viewModelScope).collect {
                followersState.value = UserFollowState.ShowFollow(it)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getUserFollowing(userName: String?) {
        if (userName.isNullOrEmpty()) {
            followersState.value = UserFollowState.Error("Invalid username $userName")
            return
        }

        followingState.value = UserFollowState.Loading
        viewModelScope.launch {
            userPager.getFollowingPage(userName).cachedIn(viewModelScope).collect {
                followingState.value = UserFollowState.ShowFollow(it)
            }
        }

    }


}