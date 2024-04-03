package com.example.githubusersearch.ui_compose.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.ui.paging.UserPager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userPager: UserPager
) : ViewModel() {
    var isUserListFetched = false
    var userList = mutableStateOf<Flow<PagingData<UserDomainModel>>>(emptyFlow())
    fun getList(listType: ListType, userName: String) {
        if (isUserListFetched)
            return
        isUserListFetched = true
        userList.value = when (listType) {
            ListType.FOLLOWERS -> userPager.getFollowersPage(userName)
            ListType.FOLLOWING -> userPager.getFollowingPage(userName)
        }.cachedIn(viewModelScope)
    }
}

enum class ListType {
    FOLLOWERS,
    FOLLOWING
}