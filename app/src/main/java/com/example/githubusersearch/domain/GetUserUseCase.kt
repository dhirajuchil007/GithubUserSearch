package com.example.githubusersearch.domain

import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.repo.UserRepo
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepo: UserRepo) {

    suspend operator fun invoke(userName: String): Result<UserDomainModel, NetworkError> {
        return when (val result = userRepo.getUser(userName)) {
            is Result.Success -> {
                val user = result.data
                Result.Success(
                    UserDomainModel(
                        avatarUrl = user?.avatar_url ?: "",
                        username = user?.login ?: "",
                        name = user?.name ?: "",
                        followers = user?.followers ?: 0,
                        following = user?.following ?: 0,
                        description = user?.bio ?: ""
                    )
                )
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }

    }
}