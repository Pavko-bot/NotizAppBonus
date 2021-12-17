package com.example.notizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ListScreenViewModel: ViewModel() {

    private var _sortedList: MutableLiveData<List<ListData>> = MutableLiveData()
    val sortedList: LiveData<List<ListData>> = _sortedList

    fun readAndSort() {
        _sortedList.value = readFromDBAndSort()
    }

    private fun readFromDBAndSort(): List<ListData> {
        val list: List<ListData> = List(4) {
            ListData(
                "hallo" + (0..120).random().toString(),
                Date((0..120).random(), 11, 11),
                (0..120).random().toString()
            )
        }
        return list.sortedByDescending { it.date }
    }

    data class ListData(val title: String, val date: Date, val content: String)
}