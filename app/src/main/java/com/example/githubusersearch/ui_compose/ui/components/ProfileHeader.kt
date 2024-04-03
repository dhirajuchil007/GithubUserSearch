package com.example.githubusersearch.ui_compose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
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
import com.example.githubusersearch.ui_compose.PROFILE_PHOTO_SIZE
import com.example.githubusersearch.ui_compose.ui.theme.GithubUserSearchTheme

@Composable
fun ProfileHeader(userDomainModel: UserDomainModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userDomainModel.avatarUrl).placeholder(R.drawable.ic_launcher_foreground)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = MEDIUM_PADDING, vertical = LARGE_PADDING)
                .clip(CircleShape)
                .size(PROFILE_PHOTO_SIZE)
        )

        Text(
            text = userDomainModel.name,
            modifier = Modifier.padding(horizontal = LARGE_PADDING, vertical = MEDIUM_PADDING),
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(id = R.color.text_title)
        )
        Text(
            text = userDomainModel.username,
            modifier = Modifier.padding(horizontal = LARGE_PADDING),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_title)
        )
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileHeaderPreview() {
    GithubUserSearchTheme {
        ProfileHeader(
            userDomainModel = testUserDomainModel
        )
    }
}
