package com.example.apilist

import android.app.Application
import androidx.room.Room
import com.example.apilist.data.database.AppDatabase

class CardApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "card_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}