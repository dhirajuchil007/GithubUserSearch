package com.example.githubusersearch.network.api

import com.example.githubusersearch.network.model.UserNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{userName}")
    suspend fun getUsers(@Path("userName") userName: String): Response<UserNetworkModel>
}