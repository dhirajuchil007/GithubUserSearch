package com.example.githubusersearch.ui_compose.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubusersearch.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow

@Composable
fun UsersList(users: LazyPagingItems<UserDomainModel>, onItemClick: (String) -> Unit) {
    val handlePagingResult = handlePagingResult(users = users)

    if (handlePagingResult)
        LazyColumn {
            items(users.itemCount) { index ->
                users[index]?.let { userDomainModel ->
                    UsersListItem(userDomainModel = userDomainModel) {
                        onItemClick(it)
                    }
                }
            }
        }

}


@Composable
fun handlePagingResult(
    users: LazyPagingItems<UserDomainModel>
): Boolean {
    val loadState = users.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            // TODO: add loading UI
            false
        }

        error != null -> {
            // TODO: add error
            false
        }

        users.itemCount == 0 -> {
            // TODO: show now items
            false
        }

        else -> true
    }
}