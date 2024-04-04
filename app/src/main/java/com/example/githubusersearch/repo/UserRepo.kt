package com.example.githubusersearch.repo

import com.example.githubusersearch.domain.NetworkError
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.domain.getNetworkErrorFromCode
import com.example.githubusersearch.network.api.ApiService
import com.example.githubusersearch.network.model.UserNetworkModel
import java.net.UnknownHostException
import javax.inject.Inject


class UserRepo @Inject constructor(private val apiService: ApiService) {

    suspend fun getUser(userName: String): Result<UserNetworkModel?, NetworkError> {
        return try {
            val response = apiService.getUsers(userName)
            if (response.isSuccessful) {
                val user = response.body()
                Result.Success(user)
            } else {
                Result.Error(getNetworkErrorFromCode(response.code() ?: 0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return parseException(e)
        }
    }

    private fun <T> parseException(e: Exception): Result<T?, NetworkError> {
        return if (e is UnknownHostException) {
            Result.Error(NetworkError.CHECK_CONNECTION)
        } else
            Result.Error(NetworkError.UNKNOWN)
    }

    suspend fun getFollowers(
        userName: String,
        page: Int,
        perPage: Int
    ): Result<List<UserNetworkModel>?, NetworkError> {
        return try {
            val response = apiService.getFollowers(userName, page, perPage)
            if (response.isSuccessful) {
                val followers = response.body()
                Result.Success(followers)
            } else {
                Result.Error(getNetworkErrorFromCode(response.code() ?: 0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return parseException(e)
        }
    }

    suspend fun getFollowing(
        userName: String,
        page: Int,
        perPage: Int
    ): Result<List<UserNetworkModel>?, NetworkError> {
        return try {
            val response = apiService.getFollowing(userName, page, perPage)
            if (response.isSuccessful) {
                val following = response.body()
                Result.Success(following)
            } else {
                Result.Error(getNetworkErrorFromCode(response.code() ?: 0))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return parseException(e)
        }
    }
}