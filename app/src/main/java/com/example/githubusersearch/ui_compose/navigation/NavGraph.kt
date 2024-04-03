package com.example.githubusersearch.ui_compose.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.githubusersearch.ui_compose.viewmodel.UserProfileViewModelCompose
import com.example.githubusersearch.ui_compose.ui.screens.SearchScreen
import com.example.githubusersearch.ui_compose.ui.screens.UserProfileScreen

@Composable
fun NavGraph() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = Route.USER_SEARCH,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable(route = Route.USER_SEARCH) {
            SearchScreen {
                controller.navigate(
                    getUserDetailsRoute(it)
                )
            }
        }
        composable(route = getUserDetailsRoute(), arguments = listOf(
            navArgument("username") {
                type = NavType.StringType
            }
        )) {
            val viewModel: UserProfileViewModelCompose = hiltViewModel()
            UserProfileScreen(userProfileState = viewModel.userDetails) {
                controller.navigate(getUserDetailsRoute(it))
            }
        }
    }
}