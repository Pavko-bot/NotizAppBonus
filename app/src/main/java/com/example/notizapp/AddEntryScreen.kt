package com.example.notizapp

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController

@Composable
fun AddEntryScreen(navController: NavHostController, viewModel: AddEntryScreenViewModel = viewModel()) {

    val showErrorMessage by viewModel.showErrorMessage.observeAsState(false)
    val title by viewModel.title.observeAsState("")
    val content by viewModel.content.observeAsState("")

    ValidationsUI(
        viewModel,
        title,
        showErrorMessage,
        content,
        navController,
    ) {
        viewModel.validateNotEmpty(title)
        if (!showErrorMessage) {
            viewModel.addEntry(title, content)
            navController.navigate(Screen.ListScreen.route)
        }
    }
}

@Composable
fun ValidationsUI(
    viewModel: AddEntryScreenViewModel,
    title: String,
    showError: Boolean,
    content: String,
    navController: NavController,
    validate: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff91a4fc))
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Entry",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        OutlineTextFieldWithErrorView(
            value = title,
            onValueChange = viewModel.updateTitle,
            singleLine = true,
            label = { Text("Title") },
            isError = showError,
            errorMsg = "Title must not be empty or it already exists"
        )

        OutlinedTextField(
            value = content,
            onValueChange = viewModel.updateContent,
            singleLine = false,
            label = { Text("Content") }
        )

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
                validate()
            }, modifier = Modifier.padding(16.dp), backgroundColor = Color.LightGray) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu_save),
                    contentDescription = "Submit"
                )

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