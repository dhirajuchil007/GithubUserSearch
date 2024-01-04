package com.example.githubusersearch.network.api

import com.example.githubusersearch.network.model.UserNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/{userName}")
    suspend fun getUsers(@Path("userName") userName: String): Response<UserNetworkModel>

    @GET("users/{userName}/followers")
    suspend fun getFollowers(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserNetworkModel>>

    @GET("users/{userName}/following")
    suspend fun getFollowing(
        @Path("userName") userName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<UserNetworkModel>>
}