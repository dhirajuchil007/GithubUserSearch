package com.example.githubusersearch.domain

import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.repo.GithubResult
import com.example.githubusersearch.repo.UserRepo
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepo: UserRepo) {

    suspend fun execute(userName:String): GithubResult<UserDomainModel> {
        return when (val result = userRepo.getUser(userName)) {
            is GithubResult.Success -> {
                val user = result.data
                GithubResult.Success(
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

            is GithubResult.Error -> {
                GithubResult.Error(result.message)
            }

            is GithubResult.Loading -> {
                GithubResult.Loading()
            }
        }

    }
}