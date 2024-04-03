package com.example.githubusersearch.ui_compose.ui.state

import com.example.githubusersearch.domain.model.UserDomainModel

sealed class UserProfileState {

    object Loading : UserProfileState()

    data class Success(val userDomainModel: UserDomainModel) : UserProfileState()

    data class Error(val message: String) : UserProfileState()
}