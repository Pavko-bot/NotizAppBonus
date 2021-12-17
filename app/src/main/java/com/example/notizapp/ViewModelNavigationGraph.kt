package com.example.notizapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun ViewModelNavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.ListScreen.route
) {
    NavHost(navController = navController, startDestination = startDestination){
        composable(Screen.ListScreen.route) {
            ListScreen(navController = navController)
        }
        composable(route = Screen.AddEntryScreen.route) {
            AddEntryScreen(navController = navController)
        }
        composable(route = Screen.DetailScreen.route + "/{title}/{content}", //TODO: Maybe add date to the route to differentiate between entries
            arguments = listOf(
                navArgument("title") {
                type = NavType.StringType;
                nullable = false
            }, navArgument("content")
            {
                type = NavType.StringType;
                nullable = true
            }
            )
        ) { entry ->
            DetailScreen(
                navController = navController,
                title = entry.arguments?.getString("title"),
                content = entry.arguments?.getString("content")
            )
        }
    }
}

