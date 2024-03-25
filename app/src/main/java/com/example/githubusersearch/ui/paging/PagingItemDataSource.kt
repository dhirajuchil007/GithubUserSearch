package com.example.githubusersearch.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubusersearch.domain.NetworkError
import com.example.githubusersearch.domain.Result
import com.example.githubusersearch.domain.model.UserDomainModel
import javax.inject.Inject

class PagingItemDataSource @Inject constructor(private val apiCallFunction: suspend (page: Int) -> Result<List<UserDomainModel>, NetworkError>) :
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
            is Result.Success -> {
                val users = result.data
                LoadResult.Page(
                    data = users ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (users?.isEmpty() == true) null else page + 1
                )
            }

            is Result.Error -> {
                LoadResult.Error(Exception(result.error.name))
            }
        }
    }
}