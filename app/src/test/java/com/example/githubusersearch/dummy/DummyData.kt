package com.example.githubusersearch.dummy

import com.example.githubusersearch.network.model.UserNetworkModel
import retrofit2.Response

fun getDummyUserNetworkModel() = UserNetworkModel(
    avatar_url = "http://example.com/avatar",
    bio = "Software engineer with a passion for open-source projects.",
    blog = "http://example.com/blog",
    company = "Example Inc.",
    created_at = "2015-08-12T15:54:20Z",
    email = "john.doe@example.com",
    events_url = "http://api.github.com/users/johndoe/events{/privacy}",
    followers = 100,
    followers_url = "http://api.github.com/users/johndoe/followers",
    following = 50,
    following_url = "http://api.github.com/users/johndoe/following{/other_user}",
    gists_url = "http://api.github.com/users/johndoe/gists{/gist_id}",
    gravatar_id = "abcdef1234567890",
    hireable = true,
    html_url = "http://github.com/johndoe",
    id = 12345678,
    location = "San Francisco, CA",
    login = "johndoe",
    name = "John Doe",
    node_id = "MDQ6VXNlcjEyMzQ1Njc4",
    organizations_url = "http://api.github.com/users/johndoe/orgs",
    public_gists = 20,
    public_repos = 30,
    received_events_url = "http://api.github.com/users/johndoe/received_events",
    repos_url = "http://api.github.com/users/johndoe/repos",
    site_admin = false,
    starred_url = "http://api.github.com/users/johndoe/starred{/owner}{/repo}",
    subscriptions_url = "http://api.github.com/users/johndoe/subscriptions",
    twitter_username = "johndoe_twitter",
    type = "User",
    updated_at = "2024-05-21T12:34:56Z",
    url = "http://api.github.com/users/johndoe"
)

fun getDummyUserNetworkModelResponse(dummyUserNetworkModel: UserNetworkModel): Response<UserNetworkModel> {
    return Response.success(dummyUserNetworkModel)
}

fun getDummyUsersList() = listOf(
    getDummyUserNetworkModel(), getDummyUserNetworkModel(), getDummyUserNetworkModel()
)

fun getDummyUsersListResponse(dummyUsersList: List<UserNetworkModel>): Response<List<UserNetworkModel>> {
    return Response.success(dummyUsersList)
}
