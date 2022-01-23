package com.example.notizapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notizapp.model.NotizRepository
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val notizRepository: NotizRepository) : ViewModel() {
    fun removeNotiz(id: Long) {
        viewModelScope.launch {
            notizRepository.removeNotiz(id)
        }
    }
}