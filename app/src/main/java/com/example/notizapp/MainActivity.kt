package com.example.notizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import java.util.*


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
        ListView(readFromDB())
}

data class ListData(val title: String, val date: Date, val content: String)

private fun readFromDB(): List<ListData> {
    val list: List<ListData> = List(3) {ListData("hallo", Date( (0..120).random(), 11, 11), "test")}
    return list
}

@Composable
private fun ListView(list: List<ListData>) {
    val sortedList: List<ListData> = list.sortedByDescending { it.date }

    LazyColumn() {
        items(items = sortedList) { listItem ->
            ListItem(title = listItem.title, date = listItem.date) // TODO: Lesen aus DB
        }
    }
    ExtendedFloatingActionButtonDemo()


}

@Composable
fun ExtendedFloatingActionButtonDemo() {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite,"") },
        text = { Text("FloatingActionButton") },
        onClick = { /*do something*/ },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

@Composable
private fun ListItem(title: String, date: Date) {

    val expanded = remember { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = title)
                Text(text = date.toString())
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
        ListView(readFromDB())
}