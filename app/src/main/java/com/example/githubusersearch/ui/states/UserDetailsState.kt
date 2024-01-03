package com.example.githubusersearch.ui.states

import com.example.githubusersearch.domain.model.UserDomainModel

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    class Error(val message: String?) : UserDetailsState()
    class ShowUser(val user: UserDomainModel?) : UserDetailsState()
}