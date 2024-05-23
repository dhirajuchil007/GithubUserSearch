package com.example.githubusersearch.ui.states

import com.example.githubusersearch.domain.model.UserDomainModel

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    data class Error(val message: String?) : UserDetailsState()
    data class ShowUser(val user: UserDomainModel?) : UserDetailsState()
}