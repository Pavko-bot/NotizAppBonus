package com.example.notizapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.notizapp.viewmodel.*


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notizRepo = (application as Application).notizRepository
        val listScreenViewModel: ListScreenViewModel by viewModels {
            ListScreenViewModelFactory(
                notizRepo
            )
        }
        val addEntryScreenViewModel: AddEntryScreenViewModel by viewModels {
            AddEntryScreenViewModelFactory(
                notizRepo
            )
        }
        val detailScreenViewModel: DetailScreenViewModel by viewModels {
            DetailScreenViewModelFactory(
                notizRepo
            )
        }
        setContent {
            MyApp(addEntryScreenViewModel, listScreenViewModel, detailScreenViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(
    addEntryScreenViewModel: AddEntryScreenViewModel,
    listScreenViewModel: ListScreenViewModel,
    detailScreenViewModel: DetailScreenViewModel
) {
    ViewModelNavigationGraph(
        addEntryScreenViewModel = addEntryScreenViewModel,
        listScreenViewModel = listScreenViewModel,
        detailScreenViewModel = detailScreenViewModel
    )
}