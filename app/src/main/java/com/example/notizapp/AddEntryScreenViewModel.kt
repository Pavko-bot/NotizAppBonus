package com.example.notizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddEntryScreenViewModel: ViewModel(){

    private var _showErrorMessage: MutableLiveData<Boolean> = MutableLiveData(false)
        val showErrorMessage: LiveData<Boolean> = _showErrorMessage

    fun validateNotEmpty(title: String) {
        _showErrorMessage.value = title.trim().isEmpty()
    }

    fun validateNotYetExisting(title: String){
        // read from DB and save in array
        // check if title does not yet exist in array
        //_showErrorMessage.value = isAlreadyExisting
    }

    private var _title: MutableLiveData<String> = MutableLiveData("")
        val title: LiveData<String> = _title

    val updateTitle = {input: String ->
        _title.value = input}

    private var _content: MutableLiveData<String> = MutableLiveData("")
    val content: LiveData<String> = _content

    val updateContent = {input: String ->
        _content.value = input}


    fun addEntry(title: String, content: String?){
        //Write to DB
    }
}