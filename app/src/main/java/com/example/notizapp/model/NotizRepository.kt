package com.example.notizapp.model

import kotlinx.coroutines.flow.Flow

class NotizRepository(private val notizDao: NotizDao) {
    val allNotizen: Flow<List<Notiz>> = notizDao.getAll()

    suspend fun addNotiz(notiz: Notiz) {
        notizDao.insert(notiz)
    }

    suspend fun removeNotiz(id: Long) {
        notizDao.delete(id)
    }
}