package com.example.githubusersearch.ui.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.githubusersearch.domain.GetFollowersUseCase
import com.example.githubusersearch.domain.GetFollowingUseCase
import com.example.githubusersearch.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPager @Inject constructor(
    val getFollowersUseCase: GetFollowersUseCase,
    val getFollowingUseCase: GetFollowingUseCase,
    val pagingConfig: PagingConfig
) {


    fun getFollowersPage(userName: String): Flow<PagingData<UserDomainModel>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                PagingItemDataSource(
                    apiCallFunction = { page -> getFollowersUseCase(userName, page) }
                )
            }
        ).flow

    }

    fun getFollowingPage(userName: String): Flow<PagingData<UserDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100
            ),
            pagingSourceFactory = {
                PagingItemDataSource(
                    apiCallFunction = { page -> getFollowingUseCase(userName, page) }
                )
            }
        ).flow

    }
}