package com.example.notizapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notiz::class], version = 3)
abstract class NotizDB : RoomDatabase() {
    abstract fun notizDao(): NotizDao

    companion object {
        @Volatile
        private var INSTANCE: NotizDB? = null

        fun getDB(context: Context): NotizDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotizDB::class.java,
                    "notiz_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}