package com.example.githubusersearch.ui_compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.domain.GetUserUseCase
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.ui.states.UserDetailsState
import com.example.githubusersearch.ui_compose.ui.state.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModelCompose @Inject constructor(
    val getUserUseCase: GetUserUseCase,
    val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    var userDetails by mutableStateOf<UserProfileState>(UserProfileState.Loading)
        private set

    init {
        val argument = checkNotNull(savedStateHandle.get<String>("username"))
        getUserDetails(argument)
    }


    private fun getUserDetails(username: String?) {
        if (username == null) {
            userDetails = UserProfileState.Error("Username cannot be empty")
            return
        }
        userDetails = UserProfileState.Loading
        viewModelScope.launch {
            when (val user = getUserUseCase.execute(username)) {
                is Result.Success -> {
                    userDetails = UserProfileState.Success(user.data)
                }

                is Result.Error -> userDetails =
                    UserProfileState.Error(user.error.name ?: "Unknown Error")
            }
        }

    }

}