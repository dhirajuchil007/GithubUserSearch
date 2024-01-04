package com.example.githubusersearch.ui.states

import androidx.paging.PagingData
import com.example.githubusersearch.domain.model.UserDomainModel

sealed class UserFollowState {
    object Loading : UserFollowState()
    class Error(val message: String?) : UserFollowState()
    class ShowFollow(val followers: PagingData<UserDomainModel>?) : UserFollowState()
}