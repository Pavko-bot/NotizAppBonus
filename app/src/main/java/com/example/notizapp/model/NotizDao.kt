package com.example.notizapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotizDao {

    @Query("SELECT * FROM Notiz")
    fun getAll(): Flow<List<Notiz>>

    @Insert
    suspend fun insert(notiz: Notiz)

    @Query("DELETE FROM Notiz WHERE id = :id")
    suspend fun delete(id: Long)
}