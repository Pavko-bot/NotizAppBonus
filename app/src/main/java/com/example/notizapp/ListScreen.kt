package com.example.notizapp

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController

@Composable
fun ListScreen(navController: NavHostController, viewModel: ListScreenViewModel = viewModel()) {
    viewModel.readAndSort()
    val sortedList by viewModel.sortedList.observeAsState(listOf<ListScreenViewModel.ListData>())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff91a4fc)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "To-Dos",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
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

        FloatingActionButton(
            onClick = { navController.navigate(Screen.AddEntryScreen.route) },
            backgroundColor = Color.LightGray,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_menu_add), contentDescription = "Add")


        }

    }
}

@Composable
private fun ListItem(title: String, date: Date, content: String, navController: NavController) {

    val expanded = remember { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    val enterDetailScreen = { navController.navigate(Screen.DetailScreen.withArgs(title, content)) }
    Surface(
        color = Color.LightGray,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable(onClick = enterDetailScreen)
            .clip(RoundedCornerShape(16.dp))
    ) {


        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = date.toString())
            }
        }
    }
}





