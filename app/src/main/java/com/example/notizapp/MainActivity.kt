package com.example.notizapp

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.util.*
import androidx.navigation.navArgument


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    Navigation()
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {
        composable(route = Screen.ListScreen.route) {
            ListScreen(navController = navController, readFromDB())
        }
        composable(route = Screen.AddEntryScreen.route) {
            AddEntryScreen(navController = navController)
        }
        composable(route = Screen.DetailScreen.route + "/{title}/{content}", //TODO: Maybe add date to the route to differentiate between entries
            arguments = listOf(navArgument("title") {
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

@Composable
fun ListScreen(navController: NavController, list: List<ListData>) {
    val sortedList: List<ListData> = list.sortedByDescending { it.date }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn() {
            items(items = sortedList) { listItem ->
                ListItem(
                    title = listItem.title,
                    date = listItem.date,
                    content = listItem.content,
                    navController = navController
                ) // TODO: Read from DB
            }
        }
        FloatingActionButton(onClick = { navController.navigate(Screen.AddEntryScreen.route) }) {
            Icon(painter = painterResource(id = R.drawable.ic_menu_add), contentDescription = "Add")
        }
    }
}

@Composable
fun AddEntryScreen(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add Entry")
        FloatingActionButton(onClick = { navController.navigate(Screen.ListScreen.route) }) {
        }
    }

}

@Composable
fun DetailScreen(navController: NavController, title: String?, content: String?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Details")
        Text(text = "Selected Entry: $title")
        Text(text = "Content: $content")
        FloatingActionButton(onClick = { navController.navigate(Screen.ListScreen.route) }) {
        }
    }

}

data class ListData(val title: String, val date: Date, val content: String)

private fun readFromDB(): List<ListData> {
    val list: List<ListData> = List(4) {
        ListData(
            "hallo" + (0..120).random().toString(),
            Date((0..120).random(), 11, 11),
            (0..120).random().toString()
        )
    }
    return list
}

@Composable
private fun ListItem(title: String, date: Date, content: String, navController: NavController) {

    val expanded = remember { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    val enterDetailScreen = { navController.navigate(Screen.DetailScreen.withArgs(title, content)) }

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = enterDetailScreen)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = title)
                Text(text = date.toString())
            }
        }
    }
}