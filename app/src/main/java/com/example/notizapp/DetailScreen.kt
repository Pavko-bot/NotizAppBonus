package com.example.notizapp

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun DetailScreen( navController: NavHostController, title: String?, content: String?, viewModel: DetailScreenViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff91a4fc))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
        Column(
            modifier = Modifier
                .absolutePadding(top = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(16.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Selected Entry: ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$title",
                    fontSize = 20.sp,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Content: ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$content",
                    fontSize = 20.sp,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            FloatingActionButton(
                onClick = { navController.navigate(Screen.ListScreen.route) },
                modifier = Modifier.padding(16.dp),
                backgroundColor = Color.LightGray
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_revert),
                    contentDescription = "Back"
                )
            }
            FloatingActionButton(onClick = {
                viewModel.removeEntry(title)
                navController.navigate(Screen.ListScreen.route)
            }, modifier = Modifier.padding(16.dp), backgroundColor = Color.LightGray) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_delete),
                    contentDescription = "Delete"
                )
            }
        }
    }

}