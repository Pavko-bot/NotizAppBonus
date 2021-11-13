package com.example.notizapp

sealed class Screen(val route: String){
    object ListScreen: Screen("list_screen")
    object AddEntryScreen: Screen("add_entry_screen")
    object DetailScreen: Screen("detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
