package com.example.githubusersearch.ui_compose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.githubusersearch.R
import com.example.githubusersearch.domain.model.UserDomainModel
import com.example.githubusersearch.ui_compose.testUserDomainModel
import com.example.githubusersearch.ui_compose.LARGE_PADDING
import com.example.githubusersearch.ui_compose.MEDIUM_PADDING
import com.example.githubusersearch.ui_compose.PROFILE_PHOTO_SIZE_LIST_ITEM
import com.example.githubusersearch.ui_compose.ui.theme.GithubUserSearchTheme

@Composable
fun UsersListItem(userDomainModel: UserDomainModel, onClick: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(userDomainModel.username)
            }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userDomainModel.avatarUrl).placeholder(R.drawable.ic_launcher_foreground)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = LARGE_PADDING, vertical = MEDIUM_PADDING)
                .clip(CircleShape)
                .size(PROFILE_PHOTO_SIZE_LIST_ITEM)
        )
        Text(
            text = userDomainModel.username,
            modifier = Modifier.padding(horizontal = MEDIUM_PADDING),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_title)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UsersListItemPreview() {
    GithubUserSearchTheme {
        UsersListItem(userDomainModel = testUserDomainModel, onClick = {})
    }
}