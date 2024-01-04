package com.example.githubusersearch.domain

import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.repo.GithubResult

import com.example.githubusersearch.repo.UserRepo
import javax.inject.Inject

class GetFollowingUseCase @Inject constructor(val userRepo: UserRepo) {

    suspend fun execute(userName: String, page: Int): GithubResult<List<UserDomainModel>> {
        return when (val result = userRepo.getFollowing(userName, page, 20)) {
            is GithubResult.Success -> {
                val followers = result.data
                GithubResult.Success(followers?.map {
                    UserDomainModel(
                        avatarUrl = it.avatar_url ?: "",
                        username = it.login ?: "",
                        name = it.name ?: "",
                        followers = it.followers ?: 0,
                        following = it.following ?: 0,
                        description = it.bio ?: ""
                    )
                } ?: emptyList())
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