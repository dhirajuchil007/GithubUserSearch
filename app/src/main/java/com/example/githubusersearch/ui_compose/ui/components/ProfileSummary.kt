package com.example.githubusersearch.ui_compose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.githubusersearch.R
import com.example.githubusersearch.ui_compose.MEDIUM_PADDING
import com.example.githubusersearch.ui_compose.ui.theme.GithubUserSearchTheme

@Composable
fun ProfileSummary(summary: String) {
    Text(
        text = summary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING),
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.body)
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileSummaryPreview() {
    GithubUserSearchTheme {
        ProfileSummary(summary = "Mobile app developer")
    }
}