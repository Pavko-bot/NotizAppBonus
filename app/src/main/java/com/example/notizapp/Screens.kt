package com.example.notizapp

sealed class Screens(val route: String) {
    object ListScreen : Screens("list_screen")
    object AddEntryScreen : Screens("add_entry_screen")
    object DetailScreen : Screens("detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
