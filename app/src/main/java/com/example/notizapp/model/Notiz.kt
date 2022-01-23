package com.example.notizapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notiz(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val content: String,
    val creationDate: String
)
