package com.example.githubusersearch.repo

sealed class GithubResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : GithubResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : GithubResult<T>(data, message)

    class Loading<T> : GithubResult<T>()

}