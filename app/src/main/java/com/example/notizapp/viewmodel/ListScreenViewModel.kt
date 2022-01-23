package com.example.notizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.notizapp.model.Notiz
import com.example.notizapp.model.NotizRepository

class ListScreenViewModel(private val notizRepository: NotizRepository) : ViewModel() {
    val notizen: LiveData<List<Notiz>> = notizRepository.allNotizen.asLiveData()
}
