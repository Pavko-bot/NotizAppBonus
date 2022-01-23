package com.example.notizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notizapp.model.NotizRepository

class DetailScreenViewModelFactory(private val notizRepository: NotizRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailScreenViewModel(notizRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}