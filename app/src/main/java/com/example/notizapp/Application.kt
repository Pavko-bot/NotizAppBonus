package com.example.notizapp

import android.app.Application
import com.example.notizapp.model.NotizDB
import com.example.notizapp.model.NotizRepository
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application : Application() {
    private val dao by lazy { NotizDB.getDB(this).notizDao() }
    val notizRepository by lazy { NotizRepository(dao) }
}
