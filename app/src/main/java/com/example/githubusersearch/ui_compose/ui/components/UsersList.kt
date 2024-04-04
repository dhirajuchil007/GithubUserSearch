package com.example.githubusersearch.ui_compose.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.githubusersearch.R
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
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            false
        }

        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                error.error.message?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.body)
                    )
                }
            }
            false
        }

        users.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No users found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )
            }
            false
        }

        else -> true
    }
}