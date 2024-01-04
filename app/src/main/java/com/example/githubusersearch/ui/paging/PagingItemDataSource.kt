package com.example.githubusersearch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.repo.GithubResult
import javax.inject.Inject

class PagingItemDataSource @Inject constructor(private val apiCallFunction: suspend (page: Int) -> GithubResult<List<UserDomainModel>>) :
    PagingSource<Int, UserDomainModel>() {

    override fun getRefreshKey(state: PagingState<Int, UserDomainModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDomainModel> {
        val page = params.key ?: 1

        return when (val result = apiCallFunction(page)) {
            is GithubResult.Success -> {
                val users = result.data
                LoadResult.Page(
                    data = users ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users?.isEmpty() == true) null else page + 1
                )
            }

            is GithubResult.Error -> {
                LoadResult.Error(Exception(result.message))
            }

            is GithubResult.Loading -> {
                LoadResult.Error(Exception("Loading"))
            }
        }
    }
}