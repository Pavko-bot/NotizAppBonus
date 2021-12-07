package com.example.notizapp

import android.R
import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.util.*
import androidx.navigation.navArgument
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.Navigation.findNavController


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
fun Navigation() { //TODO: use navController.popBackStack() at the correct position to prevent navigating back while in listScreen
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

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xff91a4fc)),
        horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "To-Dos",
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
            } }

        FloatingActionButton(onClick = { navController.navigate(Screen.AddEntryScreen.route) },backgroundColor = Color.LightGray, modifier = Modifier.padding(horizontal = 40.dp)) {
            Icon(painter = painterResource(id = R.drawable.ic_menu_add), contentDescription = "Add")
        }
    }
}

@Composable
fun AddEntryScreen(navController: NavController) {
    var title: String by remember { mutableStateOf("") }
    val titleTextUpdate = { data: String ->
        title = data
    }
    var titleTextError by remember { mutableStateOf(false) }

    var content: String by remember { mutableStateOf("") }
    val contentTextUpdate = { data: String ->
        content = data
    }

    ValidationsUI(
        title,
        titleTextUpdate,
        titleTextError,
        content,
        contentTextUpdate,
        navController
    ) {
        titleTextError = title.isEmpty()
        if (!titleTextError) {
            //TODO: Add entry in DB
            navController.navigate(Screen.ListScreen.route)
        }
    }


}

@Composable
fun DetailScreen(navController: NavController, title: String?, content: String?) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xff91a4fc))
        .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Details",
            fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        )
        Column( modifier = Modifier
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

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            horizontalArrangement = Arrangement.Center
            ){

        FloatingActionButton(onClick = { navController.navigate(Screen.ListScreen.route) }, modifier = Modifier.padding(16.dp),backgroundColor = Color.LightGray) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_revert),
                contentDescription = "Back"
            )
        }
        FloatingActionButton(onClick = {
            //TODO: Remove entry from DB
            navController.navigate(Screen.ListScreen.route)
        }, modifier = Modifier.padding(16.dp),backgroundColor = Color.LightGray) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_delete),
                contentDescription = "Delete"
            )
        }
        }
    }

}

@Composable
fun ValidationsUI(
    title: String,
    titleUpdate: (String) -> Unit,
    titleError: Boolean,
    content: String,
    contentUpdate: (String) -> Unit,
    navController: NavController,
    validate: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xff91a4fc))
        .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Add Entry",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold)
        OutlineTextFieldWithErrorView(
            value = title,
            onValueChange = titleUpdate,
            singleLine = true,
            label = { Text("Title") },
            isError = titleError,
            errorMsg = "Title must not be empty"
        )

        OutlinedTextField(
            value = content,
            onValueChange = contentUpdate,
            singleLine = false,
            label = { Text("Content") }
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            FloatingActionButton(onClick = { navController.navigate(Screen.ListScreen.route) }, modifier = Modifier.padding(16.dp),backgroundColor = Color.LightGray) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_revert),
                    contentDescription = "Back"
                )
            }

            FloatingActionButton(onClick = {
                validate()
            }, modifier = Modifier.padding(16.dp),backgroundColor = Color.LightGray) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_save),
                    contentDescription = "Submit"
                )

            }
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
                Text(text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = date.toString())
            }
        }
    }
}

@Composable
fun OutlineTextFieldWithErrorView(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    errorMsg: String = ""
) {

    Column(
        modifier = Modifier
            .padding(
                bottom = if (isError) {
                    0.dp
                } else {
                    10.dp
                }
            )
    ) {
        OutlinedTextField(
            enabled = enabled,
            readOnly = readOnly,
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            singleLine = singleLine,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors
        )

        if (isError) {
            Text(
                text = errorMsg,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
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