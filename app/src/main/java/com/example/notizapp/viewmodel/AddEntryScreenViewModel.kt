package com.example.notizapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notizapp.model.Notiz
import com.example.notizapp.model.NotizRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AddEntryScreenViewModel(private val notizRepository: NotizRepository) : ViewModel() {

    private var _showErrorMessage: MutableLiveData<Boolean> = MutableLiveData(false)
    val showErrorMessage: LiveData<Boolean> = _showErrorMessage

    fun validateNotEmpty(title: String) {
        _showErrorMessage.value = title.trim().isEmpty()
    }

    private var _title: MutableLiveData<String> = MutableLiveData("")
    val title: LiveData<String> = _title

    val updateTitle = { input: String ->
        _title.value = input
    }

    private var _content: MutableLiveData<String> = MutableLiveData("")
    val content: LiveData<String> = _content

    val updateContent = { input: String ->
        _content.value = input
    }

    val deleteInputs = { _title.value = ""; _content.value = "" }


    @RequiresApi(Build.VERSION_CODES.O)
    fun addNotiz(title: String, content: String) {
        viewModelScope.launch {
            notizRepository.addNotiz(
                Notiz(
                    0, title = title, content = content, creationDate = LocalDateTime.now().atZone(
                        ZoneId.of("CET")
                    ).format(
                        DateTimeFormatter.ofPattern("HH:mm:ss   dd.MM.yyyy")
                    )
                )
            )
        }
    }
}