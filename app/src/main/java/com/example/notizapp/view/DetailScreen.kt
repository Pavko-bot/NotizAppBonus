package com.example.notizapp.view

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notizapp.Screens
import com.example.notizapp.model.Notiz
import com.example.notizapp.viewmodel.DetailScreenViewModel

@Composable
fun DetailScreen(navController: NavHostController, notiz: Notiz, viewModel: DetailScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff91a4fc))
            .absolutePadding(left = 15.dp, right = 15.dp, bottom = 48.dp, top = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "Details",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.absolutePadding(bottom = 16.dp)
        )
        Text(
            text = "Title",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,

            )
        LazyRow(
            modifier = Modifier
                .absolutePadding(top = 4.dp, bottom = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .weight(1F),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Text(
                    text = notiz.title,
                    fontSize = 20.sp,
                )
            }
        }
        Text(
            text = "Content",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            modifier = Modifier
                .absolutePadding(top = 4.dp, bottom = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .weight(5F)
        ) {
            item {
                Text(
                    text = notiz.content,
                    fontSize = 20.sp,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .weight(1F),
            horizontalArrangement = Arrangement.Center
        ) {

            FloatingActionButton(
                onClick = { navController.navigate(Screens.ListScreen.route) },
                modifier = Modifier.absolutePadding(right = 84.dp),
                backgroundColor = Color.LightGray
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_revert),
                    contentDescription = "Back"
                )
            }
            FloatingActionButton(
                onClick = {
                    viewModel.removeNotiz(notiz.id)
                    navController.navigate(Screens.ListScreen.route)
                },
                modifier = Modifier.absolutePadding(left = 84.dp),
                backgroundColor = Color.LightGray
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_delete),
                    contentDescription = "Delete"
                )
            }
        }
    }

}