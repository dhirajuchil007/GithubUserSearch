package com.example.githubusersearch.ui_compose.navigation

object Route {
    val USER_DETAILS = "user_details"
    val USER_SEARCH = "user_search"
}

fun getUserDetailsRoute(username: String? = "{username}"): String {
    return "${Route.USER_DETAILS}/$username"
}