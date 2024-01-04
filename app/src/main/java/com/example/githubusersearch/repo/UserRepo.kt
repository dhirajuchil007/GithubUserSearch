package com.example.githubusersearch.repo

import com.example.githubusersearch.network.api.ApiService
import com.example.githubusersearch.network.model.UserNetworkModel
import javax.inject.Inject


class UserRepo @Inject constructor(private val apiService: ApiService) {

    suspend fun getUser(userName: String): GithubResult<UserNetworkModel?> {
        return try {
            val response = apiService.getUsers(userName)
            if (response.isSuccessful) {
                val user = response.body()
                GithubResult.Success(user)
            } else {
                if (response.code() == 404)
                    GithubResult.Error("User $userName not found")
                else
                    GithubResult.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GithubResult.Error(e.message)
        }
    }

    suspend fun getFollowers(
        userName: String,
        page: Int,
        perPage: Int
    ): GithubResult<List<UserNetworkModel>?> {
        return try {
            val response = apiService.getFollowers(userName, page, perPage)
            if (response.isSuccessful) {
                val followers = response.body()
                GithubResult.Success(followers)
            } else {
                if (response.code() == 404)
                    GithubResult.Error("User $userName not found")
                else if (response.code() == 403)
                    GithubResult.Error("API rate limit exceeded")
                else
                    GithubResult.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GithubResult.Error(e.message)
        }
    }

    suspend fun getFollowing(
        userName: String,
        page: Int,
        perPage: Int
    ): GithubResult<List<UserNetworkModel>?> {
        return try {
            val response = apiService.getFollowing(userName, page, perPage)
            if (response.isSuccessful) {
                val following = response.body()
                GithubResult.Success(following)
            } else {
                if (response.code() == 404)
                    GithubResult.Error("User $userName not found")
                else if (response.code() == 403)
                    GithubResult.Error("API rate limit exceeded")
                else
                    GithubResult.Error(response.message())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GithubResult.Error(e.message)
        }
    }
}