package com.example.notizapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notizapp.model.Notiz
import com.example.notizapp.view.AddEntryScreen
import com.example.notizapp.view.DetailScreen
import com.example.notizapp.view.ListScreen
import com.example.notizapp.viewmodel.AddEntryScreenViewModel
import com.example.notizapp.viewmodel.DetailScreenViewModel
import com.example.notizapp.viewmodel.ListScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewModelNavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.ListScreen.route,
    listScreenViewModel: ListScreenViewModel,
    addEntryScreenViewModel: AddEntryScreenViewModel,
    detailScreenViewModel: DetailScreenViewModel
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screens.ListScreen.route) {
            ListScreen(navController, listScreenViewModel)
        }
        composable(route = Screens.AddEntryScreen.route) {
            AddEntryScreen(navController, addEntryScreenViewModel)
        }
        composable(route = Screens.DetailScreen.route + "/{id}/{title}/{content}/{creationDate}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }, navArgument("title") {
                    type = NavType.StringType
                    nullable = false
                }, navArgument("content") {
                    type = NavType.StringType
                    nullable = false
                }, navArgument("creationDate") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            DetailScreen(
                navController,
                Notiz(
                    entry.arguments?.getString("id")?.toLong() ?: 0,
                    entry.arguments?.getString("title") ?: "No title",
                    entry.arguments?.getString("content") ?: "No content",
                    entry.arguments?.getString("creationDate") ?: "No date"
                ),
                detailScreenViewModel
            )
        }
    }
}

