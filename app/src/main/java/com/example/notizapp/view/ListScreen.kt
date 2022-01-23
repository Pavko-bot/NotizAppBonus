package com.example.notizapp.view

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.example.notizapp.Screens
import com.example.notizapp.model.Notiz
import com.example.notizapp.viewmodel.ListScreenViewModel

@Composable
fun ListScreen(navController: NavHostController, listScreenViewModel: ListScreenViewModel) {
    val notizen by listScreenViewModel.notizen.observeAsState(emptyList())
    notizen.sortedByDescending { it.creationDate }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff91a4fc))
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "To-Do's",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        LazyColumn() {
            items(items = notizen) { notiz ->
                ListItem(
                    notiz = notiz,
                    navController = navController
                )
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Screens.AddEntryScreen.route) },
            backgroundColor = Color.LightGray,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_menu_add), contentDescription = "Add")


        }

    }
}

@Composable
private fun ListItem(notiz: Notiz, navController: NavController) {


    val enterDetailScreen = {
        navController.navigate(
            Screens.DetailScreen.withArgs(
                notiz.id.toString(),
                notiz.title,
                notiz.content + " ",
                notiz.creationDate
            )
        )
    }
    Surface(
        color = Color.LightGray,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = enterDetailScreen)
    ) {


        Row(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = notiz.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.absolutePadding(bottom = 4.dp)
                )
                Text(text = notiz.creationDate)
            }
        }
    }
}





