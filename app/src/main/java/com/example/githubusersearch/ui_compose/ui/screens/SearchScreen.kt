package com.example.githubusersearch.ui_compose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.githubusersearch.R
import com.example.githubusersearch.ui_compose.MEDIUM_PADDING
import com.example.githubusersearch.ui_compose.PROFILE_PHOTO_SIZE
import com.example.githubusersearch.ui_compose.ui.theme.GithubUserSearchTheme

@Composable
fun SearchScreen(navigateToUserDetails: (String) -> Unit) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    var errorText by remember {
        mutableStateOf("")
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = null,
            modifier = Modifier.size(PROFILE_PHOTO_SIZE),

            )
        SearchBar(onValueChange = {
            text = it
            errorText = if (text.isEmpty()) {
                "Username cannot be empty"
            } else {
                ""
            }
        }, text = text, onSearch = {
            if (validateText(text))
                navigateToUserDetails(text)
            else
                errorText = "Username cannot be empty"
        })
        Text(text = errorText, color = Color.Red)
        Button(
            modifier = Modifier.padding(top = MEDIUM_PADDING),
            onClick = {
                if (validateText(text))
                    navigateToUserDetails(text)
                else
                    errorText = "Username cannot be empty"
            }, colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.primary)

            )
        ) {
            Text(text = "Search")
        }
    }

}

fun validateText(text: String): Boolean {
    return text.isNotEmpty()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onValueChange: (String) -> Unit,
    text: String,
    onSearch: () -> Unit = {}
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MEDIUM_PADDING, start = MEDIUM_PADDING, end = MEDIUM_PADDING)
            .searchBarBorder(),
        placeholder = {
            Text(
                text = "Search",
                style = MaterialTheme.typography.bodySmall,
                color = colorResource(id = R.color.placeholder)
            )
        },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.textFieldColors(

            containerColor = colorResource(id = R.color.input_background),
            cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        textStyle = MaterialTheme.typography.bodySmall,
    )


}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    GithubUserSearchTheme {
        SearchScreen({})
    }
}

fun Modifier.searchBarBorder() = then(
    composed {
        if (!isSystemInDarkTheme()) {
            border(
                width = 1.dp,
                color = Color.Black,
                shape = MaterialTheme.shapes.medium
            )
        } else {
            border(
                width = 1.dp,
                color = Color.White,
                shape = MaterialTheme.shapes.medium
            )
        }
    }
)