package com.example.githubusersearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubusersearch.domain.GetUserUseCase
import com.example.githubusersearch.repo.GithubResult
import com.example.githubusersearch.ui.states.UserDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    val getUserUseCase: GetUserUseCase
) : ViewModel() {

    val state = MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)

    fun getUser(userName: String?) {
        if (userName.isNullOrEmpty()) {
            state.value = UserDetailsState.Error("Invalid username $userName")
            return
        }

        state.value = UserDetailsState.Loading
        viewModelScope.launch {
            when (val result = getUserUseCase.execute(userName)) {
                is GithubResult.Success -> {
                    state.value = UserDetailsState.ShowUser(result.data)
                }

                is GithubResult.Error -> {
                    state.value = UserDetailsState.Error(result.message)
                }

                is GithubResult.Loading -> {
                    state.value = UserDetailsState.Loading
                }
            }
        }
    }
}