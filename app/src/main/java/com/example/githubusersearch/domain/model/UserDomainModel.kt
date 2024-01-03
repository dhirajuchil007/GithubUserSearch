package com.example.githubusersearch.domain.model

data class UserDomainModel(
    val avatarUrl: String,
    val username: String,
    val name: String,
    val followers: Int,
    val following: Int,
    val description: String
)


