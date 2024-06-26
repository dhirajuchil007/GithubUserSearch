package com.example.githubusersearch.domain

import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.repo.UserRepo
import javax.inject.Inject

class GetFollowersUseCase @Inject constructor(val userRepo: UserRepo) {

    suspend operator fun invoke(
        userName: String,
        page: Int
    ): Result<List<UserDomainModel>, NetworkError> {

        return when (val result = userRepo.getFollowers(userName, page, 20)) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> {
                val followers = result.data
                Result.Success(
                    followers?.map {
                        UserDomainModel(
                            avatarUrl = it?.avatar_url ?: "",
                            username = it?.login ?: "",
                            name = it?.name ?: "",
                            followers = it?.followers ?: 0,
                            following = it?.following ?: 0,
                            description = it?.bio ?: ""
                        )
                    } ?: emptyList()
                )
            }
        }

    }
}