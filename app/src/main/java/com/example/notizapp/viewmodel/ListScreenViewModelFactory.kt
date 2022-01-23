package com.example.notizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notizapp.model.NotizRepository

class ListScreenViewModelFactory(private val notizRepository: NotizRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListScreenViewModel(notizRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}